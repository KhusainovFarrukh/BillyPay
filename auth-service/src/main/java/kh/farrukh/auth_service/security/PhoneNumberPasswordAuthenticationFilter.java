package kh.farrukh.auth_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import kh.farrukh.feign_clients.auth.payloads.LoginRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.TokenResponseDTO;
import kh.farrukh.feign_clients.user.UserClient;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class PhoneNumberPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserClient userClient;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequestDTO loginRequestDTO;
        try {
            loginRequestDTO = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getPhoneNumber(), loginRequestDTO.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        try {
            AppUserResponseDTO appUser = userClient.searchUserByUsername(user.getUsername());
//        AuthResponseDTO authResponseDTO = tokenProvider.generateTokens(appUser);
            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            SecurityUtils.writeToResponse(tokenResponseDTO, response, objectMapper);
        } catch (FeignException.NotFound | FeignException.ServiceUnavailable e) {
            throw new UsernameNotFoundException("User not found in the database");
        }
    }
}
