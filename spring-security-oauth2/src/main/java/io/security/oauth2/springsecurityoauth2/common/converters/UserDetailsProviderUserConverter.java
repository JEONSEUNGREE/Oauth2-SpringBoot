package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.common.enums.OAuth2Config;
import io.security.oauth2.springsecurityoauth2.common.util.OAuth2Utils;
import io.security.oauth2.springsecurityoauth2.entity.User;
import io.security.oauth2.springsecurityoauth2.model.FormUser;
import io.security.oauth2.springsecurityoauth2.model.NaverUser;
import io.security.oauth2.springsecurityoauth2.model.ProviderUser;

public class UserDetailsProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (providerUserRequest.user() == null) {
            return null;
        }


        User user = providerUserRequest.user();
        return FormUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .provider(user.getProvider())
                .build();
    }
}
