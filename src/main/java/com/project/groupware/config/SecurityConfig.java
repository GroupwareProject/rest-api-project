package com.project.groupware.config;

import com.project.groupware.jwt.JwtAuthenticationEntryPoint;
import com.project.groupware.jwt.TokenProvider;
import com.project.groupware.jwt.JwtAccessDeniedHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
            , JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**", "/productimgs/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()

//                공지사항
                .antMatchers(HttpMethod.GET, "/api/v1/notice/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/notice/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/vi/notice/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "api/vi/notice/**").hasRole("ADMIN")

//                게시판
                .antMatchers("/api/v1/board/**").permitAll()

//                일정등록
                .antMatchers("/api/v1/todo/**").permitAll()

//                사원
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/v1/members/**").permitAll()
                .antMatchers("/api/v1/members/update/**").permitAll()
                .antMatchers("/api/v1/members/delete/**").permitAll()

//                회원가입
                .antMatchers(HttpMethod.POST,"/auth/signup/**").hasRole("ADMIN")

                .antMatchers("/api/**").hasAnyRole("MEMBER", "ADMIN")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()

                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-type"
                , "Access-Control-Allow-Headers", "Authorization"
                , "X-Requested-With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
