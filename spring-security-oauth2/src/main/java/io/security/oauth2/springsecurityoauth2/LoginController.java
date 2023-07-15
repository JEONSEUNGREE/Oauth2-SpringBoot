package io.security.oauth2.springsecurityoauth2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/loginPage")
    public String loginPage() {

        return "loginPage";
    }

//    @GetMapping("/user")
//    public OAuth2User user(@RequestParam String accessToken) {
//
//        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");
//        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(),Instant.MAX);
//
//        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);
//        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(oAuth2UserRequest);
//
//        return oAuth2User;
//    }
//
    @GetMapping("/user")
    public Authentication user(Authentication authentication) {

        OAuth2AuthenticationToken authentication2 = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        OAuth2AuthenticationToken authentication1 = (OAuth2AuthenticationToken) authentication;

        OAuth2User oAuth2User = (OAuth2User) authentication2.getPrincipal();

        return authentication2;
    }

    // 같은 OAuth2User 상속받지만 명확한 인증방법의 구분을 위해서 OidcUser 사용
    @GetMapping("/oauth2User")
    public OAuth2User user(@AuthenticationPrincipal OAuth2User oAuth2User) {

        // oauth2.0 provider 방식으로 받은경우
        System.out.println("oAuth2User = " + oAuth2User);

        return oAuth2User;
    }

    @GetMapping("/oidcUser")
    public OAuth2User user(@AuthenticationPrincipal OidcUser oidcUser) {

        // openId connect 방식으로 인증을 받은 경우
        System.out.println("oidcUser = " + oidcUser);

        return oidcUser;
    }


    @GetMapping("/oidc")
    public OAuth2User oidc(@RequestParam String accessToken, String idToken) {

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak");
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, Instant.now(),Instant.MAX);

        HashMap<String, Object> idTokenClaims = new HashMap<>();

        idTokenClaims.put(IdTokenClaimNames.ISS, "http:localhost:8080/realms/oauth2");
        idTokenClaims.put(IdTokenClaimNames.SUB, "OIDC0");
        idTokenClaims.put("preferred_username", "user");

        OidcIdToken oidcIdToken = new OidcIdToken(idToken, Instant.now(), Instant.MAX, idTokenClaims);
        OidcUserRequest oAuth2UserRequest = new OidcUserRequest(clientRegistration, oAuth2AccessToken, oidcIdToken);
        OidcUserService oidcUserService = new OidcUserService();
        OAuth2User oAuth2User = oidcUserService.loadUser(oAuth2UserRequest);

        return oAuth2User;

    }
}
