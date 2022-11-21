package kh.farrukh.feign_clients.auth.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("access_token_expiration")
    private String accessTokenExpiration;
    @JsonProperty("refresh_token_expiration")
    private String refreshTokenExpiration;
}
