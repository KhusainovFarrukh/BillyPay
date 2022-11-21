package kh.farrukh.feign_clients.auth;

import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_POSTFIX_REGISTER;
import static kh.farrukh.feign_clients.auth.AuthConstants.ENDPOINT_PREFIX_AUTH;

@FeignClient(name = "auth-service", path = ENDPOINT_PREFIX_AUTH)
public interface AuthClient {

    @PostMapping(ENDPOINT_POSTFIX_REGISTER)
    RegisterResponseDTO register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO);
}