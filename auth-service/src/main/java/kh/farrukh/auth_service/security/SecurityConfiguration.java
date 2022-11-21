package kh.farrukh.auth_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_LOGIN;
import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_REGISTER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationFilterConfigurer authenticationFilterConfigurer
    ) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.apply(authenticationFilterConfigurer);

        http.authorizeRequests()
                .antMatchers(
                        ENDPOINT_REGISTER,
                        ENDPOINT_LOGIN
//                        ENDPOINT_REFRESH_TOKEN
                ).permitAll();
        return http.build();
    }

}
