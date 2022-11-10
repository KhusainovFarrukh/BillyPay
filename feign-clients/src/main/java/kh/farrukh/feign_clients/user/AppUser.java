package kh.farrukh.feign_clients.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppUser {
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
}
