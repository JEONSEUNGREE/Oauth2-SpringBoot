package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.common.enums.OAuth2Config;
import io.security.oauth2.springsecurityoauth2.common.util.OAuth2Utils;
import io.security.oauth2.springsecurityoauth2.model.KakaoUser;
import io.security.oauth2.springsecurityoauth2.model.NaverUser;
import io.security.oauth2.springsecurityoauth2.model.ProviderUser;

public class OAuth2KakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuth2Config.SocialType.KAKAO.getSocialName())) {
            return null;
        }
        return new KakaoUser(
                OAuth2Utils.getOtherAttributes(providerUserRequest.oAuth2User(), "kakao_account", "profile"),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
