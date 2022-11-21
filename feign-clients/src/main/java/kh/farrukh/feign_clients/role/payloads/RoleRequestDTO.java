package kh.farrukh.feign_clients.role.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.common.security.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {

    @NotBlank
    private String title;

    private List<Permission> permissions = Collections.emptyList();

    @JsonProperty("is_default")
    @NotNull
    private Boolean isDefault = false;
}
