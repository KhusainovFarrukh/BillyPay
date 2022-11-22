package kh.farrukh.auth_service;

import kh.farrukh.common.security.TokenResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.LoginRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.LoginResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    TokenResponseDTO refreshToken(String refreshToken);

}
