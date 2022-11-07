package kh.farrukh.user.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.user.AppUser;
import kh.farrukh.user.UserRole;
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

    private UserRole role;

    public AppUserResponseDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.name = appUser.getName();
        this.email = appUser.getEmail();
        this.phoneNumber = appUser.getPhoneNumber();
        this.isEnabled = appUser.isEnabled();
        this.isLocked = appUser.isLocked();
        this.role = appUser.getRole();
    }
}