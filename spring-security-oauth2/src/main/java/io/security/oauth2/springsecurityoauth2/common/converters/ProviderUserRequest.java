package io.security.oauth2.springsecurityoauth2.common.converters;

import io.security.oauth2.springsecurityoauth2.entity.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

//record는 새로 추가된 자바 기능에서 기본적인 롬복의 getter와 tostring,hash등을 기본 지원
// 멤버변수는 private final로 선언된다
// 필드별 getter가 자동으로 생성된다
// 모든 멤버변수를 인수로 가지는 생성자를 자동으로 생성해 준다
// getter, equals, hashcode, toString과 같은 기본 메소드를 제공한다
// 컴팩트 생성자 : 클래스 생성자와 달리 레코드 생성자에는 공식적인 매개변수 목록이 없으며 이를 컴팩트 생성자라고 한다.
// 레코드는 암시적으로 fianl이며 다른 클래스를 상속받을수 없다.
public record ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, User user) {

    public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        this(clientRegistration, oAuth2User, null);
    }

    public ProviderUserRequest(User user) {
        this(null, null, user);
    }
}
