package io.security.oauth2.springsecurityoauth2.config.security;

import io.security.oauth2.springsecurityoauth2.common.authority.CustomGrantedAuthoritiesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

@Configuration
public class OAuth2AppConfig {

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomGrantedAuthoritiesMapper();
    }
}
