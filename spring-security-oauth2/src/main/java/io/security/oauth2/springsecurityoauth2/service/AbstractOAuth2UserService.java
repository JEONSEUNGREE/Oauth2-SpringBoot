package io.security.oauth2.springsecurityoauth2.service;


import io.security.oauth2.springsecurityoauth2.entity.User;
import io.security.oauth2.springsecurityoauth2.model.*;
import io.security.oauth2.springsecurityoauth2.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AbstractOAuth2UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    protected ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {

        ProviderUser providerUser = null;

        String registrationId = clientRegistration.getRegistrationId();

        switch (registrationId) {
            case "keycloak":
                providerUser = new KeycloakUser(oAuth2User, clientRegistration);
                break;
            case "google":
                providerUser = new GoogleUser(oAuth2User, clientRegistration);
                break;
            case "naver":
                providerUser = new NaverUser(oAuth2User, clientRegistration);
                break;
        }

        return providerUser;
    }

    protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

        User user = userRepository.findByUsername(providerUser.getUsername());

        if (user == null) {
            userService.register(userRequest.getClientRegistration().getRegistrationId(), providerUser);
        } else {
            System.out.println("user = " + user);
        }

    }
}
