package kh.farrukh.feign_clients.auth.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.common.security.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private List<Permission> permissions;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("access_token_expiration")
    private ZonedDateTime accessTokenExpiration;
    @JsonProperty("refresh_token_expiration")
    private ZonedDateTime refreshTokenExpiration;
}
