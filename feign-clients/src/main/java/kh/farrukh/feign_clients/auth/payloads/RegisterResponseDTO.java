package kh.farrukh.feign_clients.auth.payloads;

import kh.farrukh.common.security.TokenResponseDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {

    private AppUserResponseDTO user;

    private TokenResponseDTO token;

}
