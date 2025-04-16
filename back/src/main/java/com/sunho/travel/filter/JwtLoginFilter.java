package com.sunho.travel.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunho.travel.config.security.userDetails.AppUserDetails;
import com.sunho.travel.domain.LoginRequest;
import com.sunho.travel.util.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl("/public/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // userDetails 객체는 보안 로직을 위한 객체이므로 사용하지 않는 것이 적합
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            // loadUserByUsername이 자동적으로 실행돼서 SecurityContext에 사용자 정보 저장 AppUserDetails user = (AppUserDetails)
            // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getId(), loginRequest.getPassword()); // 비밀번호 비교도 Spring Security가 대신 도와준다.
            return this.getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 처리 중 예외 발생");
        }
    }

    // 로그인 성공 시 !!
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, Authentication authResult) throws IOException {
        String id = ((AppUserDetails) authResult.getPrincipal()).getId();
        //getAuthorities는 Collection 형태이기 때문에 iterator를 활용해서 추출
        String role = ((AppUserDetails) authResult.getPrincipal()).getAuthorities().iterator().next().getAuthority().replace("ROLE_", "").toLowerCase();
        String token = jwtTokenProvider.generateToken(id);
        Map<String, String> responseBody = makeResponseBody(id, role, token);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + "user not found" + "\"}");
    }

    public Map<String, String> makeResponseBody (String id, String role, String token){
        return Map.of("id", id, "role", role, "token", token);
    }
}