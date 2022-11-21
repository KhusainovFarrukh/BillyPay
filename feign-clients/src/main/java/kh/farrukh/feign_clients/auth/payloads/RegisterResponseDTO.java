package kh.farrukh.feign_clients.auth.payloads;

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

    // TODO: 11/21/22 implement returning token
//    private TokenResponseDTO token;

}
