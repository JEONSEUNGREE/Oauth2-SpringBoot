package io.security.oauth2.springsecurityoauth2.service;


import io.security.oauth2.springsecurityoauth2.common.converters.ProviderUserConverter;
import io.security.oauth2.springsecurityoauth2.common.converters.ProviderUserRequest;
import io.security.oauth2.springsecurityoauth2.entity.User;
import io.security.oauth2.springsecurityoauth2.model.*;
import io.security.oauth2.springsecurityoauth2.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AbstractOAuth2UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    protected ProviderUser providerUser(ProviderUserRequest providerUserRequest) {

        return providerUserConverter.converter(providerUserRequest);
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
