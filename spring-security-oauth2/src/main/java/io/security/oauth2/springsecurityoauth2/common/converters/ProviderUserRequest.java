package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.entity.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

//record는 새로 추가된 자바 기능에서 기본적인 롬복의 getter와 tostring,hash등을 기본 지원
public record ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, User user) {

    public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        this(clientRegistration, oAuth2User, null);
    }

    public ProviderUserRequest(User user) {
        this(null, null, user);
    }
}
