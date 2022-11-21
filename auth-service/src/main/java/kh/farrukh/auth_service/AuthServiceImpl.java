package kh.farrukh.auth_service;

import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
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
}
