package com.parket.webproject.cofig;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // spring security 웹 보안기능 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        return  http

                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/authentication/check").permitAll()
                        .requestMatchers("/cart/add").authenticated()
                        .requestMatchers("/order/**").authenticated()
                        .requestMatchers("/**").permitAll()
                )
                // 로그인 필요한 페이지 401 보내기
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                        })
                )
//                .csrf(httpCsrf -> httpCsrf.disable())
//                .authorizeHttpRequests(authorizeRequest-> authorizeRequest
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/**").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/member/register","/loginProcess").permitAll()
//                        // 관리자 페이지 권한
//                        // 공지사항 목록/상세 조회는 모든 사용자 접근 허용
//                        .requestMatchers("/noti/list", "/noti/detail/**").permitAll()
//                        // 작성/수정/삭제 페이지는 ADMIN 권한만 허용
//                        .requestMatchers("/noti/noticeAdd", "/noti/noticeModify/**", "/noti/delete/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/menu/add", "/menu/update").hasAnyAuthority("ADMIN", "MANAGER")
//                        .requestMatchers(HttpMethod.DELETE, "/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")
//                        .anyRequest().authenticated()
//                )
                .formLogin(formLoginConfig->formLoginConfig
                        .loginPage("/member/login")
                        .loginProcessingUrl("/loginProcess")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logoutConfig->logoutConfig
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Static 요소를 security 허용해줌
    @Bean
    public WebSecurityCustomizer configurer() {
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }
}

