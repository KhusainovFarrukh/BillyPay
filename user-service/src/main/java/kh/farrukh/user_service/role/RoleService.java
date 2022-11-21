package kh.farrukh.user_service.role;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.role.payloads.RoleRequestDTO;
import kh.farrukh.feign_clients.role.payloads.RoleResponseDTO;

public interface RoleService {

    PagingResponse<RoleResponseDTO> getRoles(int page, int pageSize);

    RoleResponseDTO getRoleById(Long id);

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    RoleResponseDTO updateRole(long id, RoleRequestDTO roleRequestDTO);

    void deleteRole(long id);
}
