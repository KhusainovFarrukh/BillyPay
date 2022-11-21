package kh.farrukh.feign_clients.role;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.role.payloads.RoleRequestDTO;
import kh.farrukh.feign_clients.role.payloads.RoleResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.role.RoleConstants.*;

public interface RoleClient {

    @GetMapping
    PagingResponse<RoleResponseDTO> getRoles(
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    );

    @GetMapping(ENDPOINT_POSTFIX_ID)
    RoleResponseDTO getRoleById(@PathVariable(PARAM_ID) long id);

    @PostMapping
    RoleResponseDTO addRole(@Valid @RequestBody RoleRequestDTO roleDto);

    @PutMapping(ENDPOINT_POSTFIX_ID)
    RoleResponseDTO updateRole(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody RoleRequestDTO roleDto
    );

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    void deleteRole(@PathVariable(PARAM_ID) long id);
}
