package kh.farrukh.auth_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kh.farrukh.feign_clients.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_LOGIN;

@Component
@RequiredArgsConstructor
public class AuthenticationFilterConfigurer extends AbstractHttpConfigurer<AuthenticationFilterConfigurer, HttpSecurity> {

    //        private final TokenProvider tokenProvider;
    private final UserClient userClient;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        PhoneNumberPasswordAuthenticationFilter filter = new PhoneNumberPasswordAuthenticationFilter(
                userClient, objectMapper, httpSecurity.getSharedObject(AuthenticationManager.class)
        );
        filter.setFilterProcessesUrl(ENDPOINT_LOGIN);
        httpSecurity.addFilter(filter);
    }
}