package com.sparta.vikingband.config;

import com.sparta.vikingband.jwt.JwtAuthFilter;
import com.sparta.vikingband.jwt.JwtUtil;
import com.sparta.vikingband.security.CustomAccessDeniedHandler;
import com.sparta.vikingband.security.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .antMatchers("/api/v1/auth/**","/",
                        "/v2/api-docs","/v3/api-docs","/swagger-ui*/**","/swagger-resources/**"
                        ,"/webjars/**","/swagger/**","/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //로그인 된 후 토큰없이 자동 인증되는 것을 방지
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //회원가입, 로그인,조회까지는 security 인증 없이도 가능함
        http.authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/board").permitAll()
                .antMatchers(HttpMethod.GET, "/api/board/**").permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //Controller 단 전에 시큐리티에서 검사하므로 따로 Exceptionhandler가 필요하다
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        return http.build();
    }
}
