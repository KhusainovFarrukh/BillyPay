package kh.farrukh.auth_service;

import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

}
