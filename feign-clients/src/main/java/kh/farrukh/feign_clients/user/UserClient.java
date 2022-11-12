package kh.farrukh.feign_clients.user;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import kh.farrukh.feign_clients.user.payloads.UserPasswordRequestDTO;
import kh.farrukh.feign_clients.user.payloads.UserRoleRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.user.UserConstants.*;

@FeignClient(name = "user-service", path = ENDPOINT_USER)
public interface UserClient {

    @GetMapping
    PagingResponse<AppUserResponseDTO> getUsers(
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    );

    @GetMapping(ENDPOINT_POSTFIX_ID)
    AppUserResponseDTO getUserById(@PathVariable(PARAM_ID) long id);

    @PostMapping
    AppUserResponseDTO createUser(@RequestBody AppUserRequestDTO userRequestDTO);

    @PutMapping(ENDPOINT_POSTFIX_ID)
    AppUserResponseDTO updateUser(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody AppUserRequestDTO appUserDto
    );

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    Void deleteUser(@PathVariable(PARAM_ID) long id);

    @PatchMapping(ENDPOINT_POSTFIX_USER_ROLE)
    AppUserResponseDTO setUserRole(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody UserRoleRequestDTO roleDto
    );

    @PatchMapping(ENDPOINT_POSTFIX_USER_PASSWORD)
    AppUserResponseDTO setUserPassword(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody UserPasswordRequestDTO passwordDto
    );
}