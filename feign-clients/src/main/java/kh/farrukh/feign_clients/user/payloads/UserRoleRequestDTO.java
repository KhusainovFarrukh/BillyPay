package kh.farrukh.feign_clients.user.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * It's a DTO that represents a role
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestDTO {

    @JsonProperty("role_id")
    @NotNull(message = "Role id must not be null")
    private Long roleId;
}
