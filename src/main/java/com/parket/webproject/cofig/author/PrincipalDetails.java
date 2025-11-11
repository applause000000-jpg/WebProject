package com.parket.webproject.cofig.author;


import com.parket.webproject.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(()->{return user.getRole();}); //여기수정 - 수정전코드
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole())); //수정코드
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


/*  //    authorities.add(()->{return user.getRole();}); //여기수정 수정전코드
위에 코드 그대로 사용하려면 SecurityConfig에 아래 코드 추가
@Configuration
public class SecurityConfig {

    //  ROLE_ prefix 자동 추가 방지
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setDefaultRolePrefix(""); //  ROLE_ prefix 제거
        return handler;
    }

    //  보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // ADMIN만 접근 가능
                .requestMatchers("/noti/noticeAdd", "/noti/noticeModify/**", "/noti/delete/**")
                    .hasRole("ADMIN")
                // 나머지는 모두 접근 허용
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}

 */