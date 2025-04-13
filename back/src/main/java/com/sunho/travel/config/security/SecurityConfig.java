package com.sunho.travel.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sunho.travel.filter.JwtAuthenticationFilter;
import com.sunho.travel.filter.JwtLoginFilter;
import com.sunho.travel.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService, AuthenticationManager authenticationManager) throws Exception {
        http.formLogin(formLogin -> formLogin.disable())
                .httpBasic(basic -> basic.disable())
                // 아래의 cors 설정은 DispatcherServlet 이후 처리되는 요청에 적용되는 설정이다. (애초에 들어올 수 있는 요청인지 판단),
                // 다른 출처(도메인) 간의 요청을 허용할 것인지 결정하는 보안 정책입니다. cors().and()로 하면 WebMvcConfig
                // 참고해서설정을 해준다.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                /*
                 * JWT 환경에선 일반적으로 disable을 사용한다. CSRF -> 인증된 사용자(로그인된 사용자)의 브라우저를 악용해 서버에 의도치 않은
                 * 요청을 보내는 공격이다.
                 */
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(o -> o.disable())) // iframe 허용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/public/**").permitAll()
                        /* .anyRequest().authenticated()를 추가해주면 인증된 사용자만 접근할 수 있도록 제한하는 설정입니다. */
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/member/**").hasAnyRole("MEMBER", "ADMIN")
                        //위의 조건에 없는 모든 요청은 403 Forbidden
                        .anyRequest().denyAll() )
                .addFilter(new JwtLoginFilter(authenticationManager, jwtTokenProvider))
                // JwtAuthenticationFilter를 Spring Security의 필터 체인에 추가하면서
                // UsernamePasswordAuthenticationFilter 바로 전에 실행되도록 등록
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true); // 인증 정보 포함 허용 (쿠키 등)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 적용
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 자동으로 생성이 안 되므로 아래와 같이 정의를 해줘야 한다.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MEMBER");
        return roleHierarchy;
    }
}
