package io.security.oauth2.springsecurityoauth2.service;

import io.security.oauth2.springsecurityoauth2.common.converters.ProviderUserRequest;
import io.security.oauth2.springsecurityoauth2.model.PrincipalUser;
import io.security.oauth2.springsecurityoauth2.model.ProviderUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();

        // change user-name-attribute when use openid connect
//        ClientRegistration.withClientRegistration(userRequest.getClientRegistration())
//                .userNameAttributeName("sub")
//                .build();

        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();

        OidcUser oidcUser = oidcUserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oidcUser);

        ProviderUser providerUser = providerUser(providerUserRequest);

        // 회원가입

        super.register(providerUser, userRequest);

        return new PrincipalUser(providerUser);
    }
}
