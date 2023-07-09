package io.security.oauth2.springsecurityoauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/")
    public String index() {

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloack");

        String clientId = clientRegistration.getClientId();
        String redirectUri = clientRegistration.getRedirectUri();

        System.out.println(clientId);
        System.out.println(redirectUri);

        return "index";
    }
}
