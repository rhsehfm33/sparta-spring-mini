package com.sparta.vikingband.config;

import com.sparta.vikingband.jwt.JwtAuthFilter;
import com.sparta.vikingband.jwt.JwtUtil;
import com.sparta.vikingband.security.CustomAccessDeniedHandler;
import com.sparta.vikingband.security.CustomAuthenticationEntryPoint;
import com.sparta.vikingband.security.UserDetailsServiceImpl;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //로그인 된 후 토큰없이 자동 인증되는 것을 방지
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //회원가입, 로그인,조회까지는 security 인증 없이도 가능함
        http.authorizeRequests()
                .antMatchers("/docs").permitAll()
                .antMatchers("/api/members/signup").permitAll()
                .antMatchers("/api/members/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/studies/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/board/**").permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //Controller 단 전에 시큐리티에서 검사하므로 따로 Exceptionhandler가 필요하다
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://viking-band-fe.vercel.app")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedMethods(); // allow the HTTP OPTIONS method for preflight requests
    }
}
