package io.security.oauth2.springsecurityoauth2;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2ClientConfig {


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(keycloackClientRegistration());
    }

    private ClientRegistration keycloackClientRegistration() {

        return ClientRegistrations.fromIssuerLocation("http://localhost:8080/realms/oauth2")
                .clientId("oauth2-client-app")
                .clientSecret("BEcxjQZth2VU0A9llS8hwE3qJXvNMLXR")
                .redirectUri("http://localhost:8081/login/oauth2/code/keycloak")
                .build();
    }
}
