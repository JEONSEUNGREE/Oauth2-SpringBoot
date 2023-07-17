package io.security.oauth2.springsecurityoauth2.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

// kakao는 openid 와 code와 다르다
public class KakaoUser extends OAuth2ProviderUser{

    private Map<String, Object> otherAttributes;

    public KakaoUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        // 네이버는 response 한 뎁스가 더 있음
        super(attributes.getSubAttributes(), oAuth2User, clientRegistration);
        this.otherAttributes = attributes.getOtherAttributes();
    }

    @Override
    public String getId() {
        // 식별자
        return (String) getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        // 실제 아이디
        return (String) otherAttributes.get("nickname");
    }

    @Override
    public String getPicture() {
        return (String) otherAttributes.get("profile_image_url");
    }

}
