package io.security.oauth2.springsecurityoauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authRequest ->
                authRequest
                        .anyRequest() .permitAll()
                        );
        // 로그인 페이지를 변경
        http.oauth2Login(oauth2 -> oauth2.loginPage("/loginPage"));

        return http.build();
    }
}
