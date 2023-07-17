package io.security.oauth2.springsecurityoauth2.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;


public class NaverUser extends OAuth2ProviderUser{

    public NaverUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        // 네이버는 response 한 뎁스가 더 있음
        super(attributes.getSubAttributes(), oAuth2User, clientRegistration);
    }

    @Override
    public String getId() {
        // 식별자
        return (String) getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        // 실제 아이디
        return (String) getAttributes().get("email");
    }

    @Override
    public String getPicture() {
        return (String) getAttributes().get("profile_image");
    }
}
