package kh.farrukh.stats_service.security;

import kh.farrukh.common.security.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static kh.farrukh.feign_clients.stats.StatsConstants.ENDPOINT_STATS;

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
                .antMatchers(HttpMethod.GET, ENDPOINT_STATS).hasAuthority(Permission.STATS_GET.name())
                .antMatchers(HttpMethod.POST, ENDPOINT_STATS).hasAuthority(Permission.STATS_POST.name())
                .antMatchers(HttpMethod.PUT, ENDPOINT_STATS).hasAuthority(Permission.STATS_PUT.name())
                .antMatchers(HttpMethod.PATCH, ENDPOINT_STATS).hasAuthority(Permission.STATS_PATCH.name())
                .antMatchers(HttpMethod.DELETE, ENDPOINT_STATS).hasAuthority(Permission.STATS_DELETE.name());

        return http.build();
    }
}
