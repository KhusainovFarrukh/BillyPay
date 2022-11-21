package kh.farrukh.user_service.role;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.role.payloads.RoleRequestDTO;
import kh.farrukh.feign_clients.role.payloads.RoleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.role.RoleConstants.*;

@RestController
@RequestMapping(ENDPOINT_ROLE)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<PagingResponse<RoleResponseDTO>> getRoles(
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(roleService.getRoles(page, pageSize));
    }

    @GetMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable(PARAM_ID) long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> addRole(@Valid @RequestBody RoleRequestDTO roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDto));
    }

    @PutMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody RoleRequestDTO roleDto
    ) {
        return ResponseEntity.ok(roleService.updateRole(id, roleDto));
    }

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<Void> deleteRole(@PathVariable(PARAM_ID) long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}