package io.security.oauth2.springsecurityoauth2;

import io.security.oauth2.springsecurityoauth2.service.CustomOAuth2UserService;
import io.security.oauth2.springsecurityoauth2.service.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class OAuth2ClientConfig {

    @Autowired
    private CustomOAuth2UserService customOAUth2UserService;

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().antMatchers().antMatchers("/static/js/*", "static/images/**", "/static/css/**", "/static/icomoon/**"));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authRequest -> authRequest
//                .antMatchers("/api/user").access("hasAnyRole('SCOPE_profile', 'SCOPE_email')")
//                .antMatchers("/api/oidc").access("hasAnyRole('SCOPE_openid')")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated());

//        http
//                .formLogin().loginPage("/login").loginProcessingUrl("/loginProc").defaultSuccessUrl("/").permitAll();

        http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAUth2UserService)
                        .oidcUserService(customOidcUserService)));

        http
                .logout().logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomGrantedAuthoritiesMapper();
    }


    //##############################oauth2Client는 인가만 적용함##########################################
    // Oauth2ClientConfigurer에서 authorizationCodeGrantConfigure 초기화를 담당
    // 임시코드 발급 클래스는 oauth2Login과 동일
    // OAuth2AuthorizationRequestRedirectFilter, OAuth2AuthorizationRequestResolver - 인가서버로부터 코드 가져오는 역할 하는 클래스들
    // OAuth2AuthorizationCodeGrantFilter - 코드를 토큰으로 교환하기 위한 클래스
    // OAuth2AuthorizedClientRepository - OAuth2AuthorizedClient를 저장 후 클라이언트의 Redirect Uri로 이동
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authRequest -> authRequest.anyRequest().authenticated());
//        http.oauth2Client(Customizer.withDefaults());
//// Customizer 인터페이스 안에 withDefaults()로 두면 커스텀한 설정을 하지않겠다는 의미이다.
//        return http.build();
//    }
    //##############################oauth2Client는 인가만 적용함##########################################

//##############################oauth2Login은 인가뿐 아니라 자체 사용자 인증까지 함##########################################
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authRequest -> authRequest.anyRequest().authenticated());
//        http.oauth2Login(oauth2 -> oauth2.loginPage("/login").authorizationEndpoint(authorizationEndpointConfig ->
//                        authorizationEndpointConfig.baseUri("/oauth2/v1/authorization"))
//                .redirectionEndpoint().baseUri("/login/oauth2/v1/code/*"));
//// Customizer 인터페이스 안에 withDefaults()로 두면 커스텀한 설정을 하지않겠다는 의미이다.
//        return http.build();
//    }
//##############################oauth2Login은 인가뿐아 아니라 자체 사용자 인증까지 함##########################################

//##############################oauth2Logout##########################################
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authRequest -> authRequest.anyRequest().authenticated());
//        http.oauth2Login(Customizer.withDefaults());
//        http.logout()
//                .logoutSuccessHandler(oidcLogoutSuccessHandler())
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID");
//// Customizer 인터페이스 안에 withDefaults()로 두면 커스텀한 설정을 하지않겠다는 의미이다.
//        return http.build();
//    }
//
//    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
//        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        successHandler.setPostLogoutRedirectUri("http://localhost:8081/login");
//
//        return successHandler;
//    }
// ##############################oauth2Logout##########################################
}