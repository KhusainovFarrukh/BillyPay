package kh.farrukh.auth_service;

import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_POSTFIX_REGISTER;
import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_PREFIX_AUTH;

@RestController
@RequestMapping(ENDPOINT_PREFIX_AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ENDPOINT_POSTFIX_REGISTER)
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authService.register(registerRequestDTO));
    }
}
