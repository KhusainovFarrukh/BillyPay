package kh.farrukh.auth_service;

import kh.farrukh.common.security.TokenResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.LoginRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.LoginResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.auth.AuthConstants.*;

@RestController
@RequestMapping(ENDPOINT_PREFIX_AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ENDPOINT_POSTFIX_REGISTER)
    public ResponseEntity<RegisterResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO registerRequestDTO
    ) {
        return ResponseEntity.ok(authService.register(registerRequestDTO));
    }

    @PostMapping(ENDPOINT_POSTFIX_LOGIN)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @GetMapping(ENDPOINT_POSTFIX_REFRESH_TOKEN)
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
