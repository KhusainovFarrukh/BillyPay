package kh.farrukh.user_service.role;

import kh.farrukh.feign_clients.role.payloads.RoleRequestDTO;
import kh.farrukh.feign_clients.role.payloads.RoleResponseDTO;
import org.springframework.beans.BeanUtils;

public class RoleMappers {

    public static RoleResponseDTO toRoleResponseDTO(Role role) {
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        BeanUtils.copyProperties(role, roleResponseDTO);
        return roleResponseDTO;
    }

    public static Role toRole(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleRequestDTO, role);
        return role;
    }
}
