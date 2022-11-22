package kh.farrukh.bill_service.security;

import kh.farrukh.common.security.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static kh.farrukh.feign_clients.bill.BillConstants.ENDPOINT_BILL;

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
                .antMatchers(HttpMethod.GET, ENDPOINT_BILL).hasAuthority(Permission.BILL_GET.name())
                .antMatchers(HttpMethod.POST, ENDPOINT_BILL).hasAuthority(Permission.BILL_POST.name())
                .antMatchers(HttpMethod.PUT, ENDPOINT_BILL).hasAuthority(Permission.BILL_PUT.name())
                .antMatchers(HttpMethod.PATCH, ENDPOINT_BILL).hasAuthority(Permission.BILL_PATCH.name())
                .antMatchers(HttpMethod.DELETE, ENDPOINT_BILL).hasAuthority(Permission.BILL_DELETE.name());

        return http.build();
    }
}
