package kh.farrukh.feign_clients.auth.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @NotBlank
    private String password;
}
