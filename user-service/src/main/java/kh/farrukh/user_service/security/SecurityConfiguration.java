package kh.farrukh.user_service.security;

import kh.farrukh.common.security.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static kh.farrukh.feign_clients.role.RoleConstants.ENDPOINT_ROLE;
import static kh.farrukh.feign_clients.user.UserConstants.ENDPOINT_SEARCH_BY_USERNAME;
import static kh.farrukh.feign_clients.user.UserConstants.ENDPOINT_USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthorizationFilter jwtAuthorizationFilter
    ) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, ENDPOINT_SEARCH_BY_USERNAME).permitAll()
                .antMatchers(HttpMethod.POST, ENDPOINT_USER).permitAll()
                .antMatchers(HttpMethod.GET, ENDPOINT_USER).hasAuthority(Permission.USER_GET.name())
                .antMatchers(HttpMethod.PUT, ENDPOINT_USER).hasAuthority(Permission.USER_PUT.name())
                .antMatchers(HttpMethod.PATCH, ENDPOINT_USER).hasAuthority(Permission.USER_PATCH.name())
                .antMatchers(HttpMethod.DELETE, ENDPOINT_USER).hasAuthority(Permission.USER_DELETE.name())
                .antMatchers(HttpMethod.GET, ENDPOINT_ROLE).hasAuthority(Permission.ROLE_GET.name())
                .antMatchers(HttpMethod.POST, ENDPOINT_ROLE).hasAuthority(Permission.ROLE_POST.name())
                .antMatchers(HttpMethod.PUT, ENDPOINT_ROLE).hasAuthority(Permission.ROLE_PUT.name())
                .antMatchers(HttpMethod.PATCH, ENDPOINT_ROLE).hasAuthority(Permission.ROLE_PATCH.name())
                .antMatchers(HttpMethod.DELETE, ENDPOINT_ROLE).hasAuthority(Permission.ROLE_DELETE.name());

        return http.build();
    }
}
