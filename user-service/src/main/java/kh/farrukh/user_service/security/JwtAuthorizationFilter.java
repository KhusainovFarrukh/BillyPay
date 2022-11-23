package kh.farrukh.user_service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import kh.farrukh.common.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kh.farrukh.common.security.TokenProvider.KEY_PERMISSIONS;
import static kh.farrukh.feign_clients.user.UserConstants.ENDPOINT_SEARCH_BY_USERNAME;
import static kh.farrukh.feign_clients.user.UserConstants.ENDPOINT_USER;

// TODO: 11/22/22 move to common module and remove from each service
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains(ENDPOINT_SEARCH_BY_USERNAME) ||
                (request.getRequestURI().contains(ENDPOINT_USER) && request.getMethod().equals(HttpMethod.POST.name()));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {
        try {
            // TODO: 11/22/22 move to utils class in common module
            DecodedJWT decodedJWT = tokenProvider.validateToken(request.getHeader(HttpHeaders.AUTHORIZATION), false);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    decodedJWT.getSubject(),
                    null,
                    // TODO: 11/22/22 get permissions from user-service, because it can be changed
                    decodedJWT.getClaim(KEY_PERMISSIONS).asList(String.class)
                            .stream().map(SimpleGrantedAuthority::new).toList()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // TODO: 11/22/22 implement error handling
//        } catch (AlgorithmMismatchException exception) {
//            resolver.resolveException(request, response, null, new WrongTypeTokenException());
//        } catch (SignatureVerificationException exception) {
//            resolver.resolveException(request, response, null, new InvalidSignatureTokenException());
//        } catch (TokenExpiredException exception) {
//            resolver.resolveException(request, response, null, new ExpiredTokenException());
//        } catch (InvalidClaimException exception) {
//            resolver.resolveException(request, response, null, new InvalidRoleTokenException());
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            resolver.resolveException(request, response, null, new UnknownTokenException());
//        }
    }
}
