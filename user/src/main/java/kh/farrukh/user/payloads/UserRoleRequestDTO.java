package kh.farrukh.user.payloads;

import kh.farrukh.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * It's a DTO that represents a role
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestDTO {

    @NotNull(message = "Role must not be null")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
