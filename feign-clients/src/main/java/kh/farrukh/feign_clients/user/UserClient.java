package kh.farrukh.feign_clients.user;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import kh.farrukh.feign_clients.user.payloads.UserPasswordRequestDTO;
import kh.farrukh.feign_clients.user.payloads.UserRoleRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.user.UserConstants.*;

@FeignClient(name = "user-service", path = ENDPOINT_USER)
public interface UserClient {

//    todo: secure the methods which are used in other services

    @GetMapping
    PagingResponse<AppUserResponseDTO> getUsers(
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    );

    @GetMapping(ENDPOINT_POSTFIX_ID)
    AppUserResponseDTO getUserById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable(PARAM_ID) long id
    );

    @GetMapping(ENDPOINT_POSTFIX_SEARCH_BY_USERNAME)
    AppUserResponseDTO searchUserByUsername(@RequestParam(PARAM_PHONE_NUMBER) String phoneNumber);

    @PostMapping
    AppUserResponseDTO createUser(
            @RequestBody AppUserRequestDTO userRequestDTO
    );

    @PutMapping(ENDPOINT_POSTFIX_ID)
    AppUserResponseDTO updateUser(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody AppUserRequestDTO appUserDto
    );

    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    void deleteUser(@PathVariable(PARAM_ID) long id);

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