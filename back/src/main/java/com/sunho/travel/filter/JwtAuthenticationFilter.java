package com.sunho.travel.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunho.travel.config.security.userDetails.AppUserDetails;
import com.sunho.travel.util.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 요청 헤더에서 Authorization 추출
        String authorizationHeader = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        // Bearer로 시작하는지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 이후 문자열
            if (jwtTokenProvider.validateToken(token)) {
                
                if(uri.startsWith("/api")){
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("useable Token");
                    return;
                }

                String id = jwtTokenProvider.getIdFromToken(token);
                // 토큰 안에 사용자의 정보가 있으니깐 굳이 비밀번호 검증이 필요 없다. 따라서 autenticate()과정이 필요 없다.
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                // 사용자의 세션 정보, IP 주소, User-Agent 등
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // JWT는 stateless 방식이다 == Not Session 방식 -> 로그인 후 서버는 아무 것도 저장하지 않는다.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                if (!uri.startsWith("/public")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("token expired");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
