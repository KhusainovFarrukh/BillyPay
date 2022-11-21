package kh.farrukh.feign_clients.role.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.common.security.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {

    private Long id;

    private String title;

    private List<Permission> permissions;

    @JsonProperty("is_default")
    private Boolean isDefault;
}