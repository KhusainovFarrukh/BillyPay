package kh.farrukh.feign_clients.user.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponseDTO {

    private long id;

    private String name;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("is_enabled")
    // TODO: 6/7/22 set default to false and implement phone verification OTP
    private boolean isEnabled = true;

    @JsonProperty("is_locked")
    private boolean isLocked = false;

    private UserRoleDTO role;
}
