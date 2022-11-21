package kh.farrukh.auth_service;

import feign.FeignException;
import kh.farrukh.feign_clients.auth.payloads.LoginRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.TokenResponseDTO;
import kh.farrukh.feign_clients.user.UserClient;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        AppUserResponseDTO appUserResponseDTO = userClient.createUser(AuthMappers.toAppUserRequestDTO(registerRequestDTO));
        return AuthMappers.toRegisterResponseDTO(appUserResponseDTO);
    }

    @Override
    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            AppUserResponseDTO appUserResponseDTO = userClient.searchUserByUsername(loginRequestDTO.getPhoneNumber());
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), appUserResponseDTO.getEncodedPassword())) {
                // TODO: 11/21/22 custom exception
                throw new RuntimeException("Wrong password");
            }
            return new TokenResponseDTO();
        } catch (FeignException.ServiceUnavailable | FeignException.NotFound e) {
            // TODO: 11/21/22 custom exception
            throw new RuntimeException("Wrong phone number");
        }
    }
}
