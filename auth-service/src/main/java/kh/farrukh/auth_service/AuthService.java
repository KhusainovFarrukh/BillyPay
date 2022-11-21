package kh.farrukh.auth_service;

import kh.farrukh.feign_clients.auth.payloads.LoginRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.TokenResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    TokenResponseDTO login(LoginRequestDTO loginRequestDTO);

}
