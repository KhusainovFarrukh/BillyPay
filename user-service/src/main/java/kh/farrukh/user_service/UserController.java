package kh.farrukh.user_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import kh.farrukh.feign_clients.user.payloads.UserPasswordRequestDTO;
import kh.farrukh.feign_clients.user.payloads.UserRoleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.feign_clients.user.UserConstants.*;

/**
 * Controller that exposes endpoints for managing users
 */
@RestController
@RequestMapping(ENDPOINT_USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * It returns a list of users.
     *
     * @param page     The page number to return.
     * @param pageSize The number of items to be returned in a page.
     * @return A ResponseEntity with a PagingResponse of AppUser objects.
     */
    @GetMapping
    public ResponseEntity<PagingResponse<AppUserResponseDTO>> getUsers(
            @RequestParam(name = PARAM_PAGE, defaultValue = "1") int page,
            @RequestParam(name = PARAM_PAGE_SIZE, defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(userService.getUsers(page, pageSize));
    }

    /**
     * It returns a user by id.
     *
     * @param id The id of the user you want to get.
     * @return A ResponseEntity with found AppUser.
     */
    @GetMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable(PARAM_ID) long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> createUser(@RequestBody AppUserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.createUser(userRequestDTO));
    }

    /**
     * This function updates a user.
     *
     * @param id         The id of the user to update
     * @param appUserDto The user values that we want to update.
     * @return A ResponseEntity with the updated AppUser object and HttpStatus.
     */
    @PutMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<AppUserResponseDTO> updateUser(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody AppUserRequestDTO appUserDto
    ) {
        return ResponseEntity.ok(userService.updateUser(id, appUserDto));
    }

    /**
     * This function deletes a user from a language
     *
     * @param id The id of the user to delete
     * @return A ResponseEntity with HttpStatus.
     */
    @DeleteMapping(ENDPOINT_POSTFIX_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable(PARAM_ID) long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * This function sets the role of the user with the given id,
     * to the role given in the request body.
     *
     * @param id      The id of the user to be updated
     * @param roleDto This is the object that contains the role that we want to set the user to.
     * @return A ResponseEntity with the updated AppUser object and HttpStatus.
     */
    @PatchMapping(ENDPOINT_POSTFIX_USER_ROLE)
    public ResponseEntity<AppUserResponseDTO> setUserRole(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody UserRoleRequestDTO roleDto
    ) {
        return ResponseEntity.ok(userService.setUserRole(id, roleDto));
    }

    /**
     * This function sets the image of the user with the given id,
     * to the image given in the request body.
     *
     * @param id       The id of the user to be updated
     * @param imageDto This is the object that contains the image id that we want to set the user to.
     * @return A ResponseEntity with the updated AppUser object and HttpStatus.
     */
//    @PatchMapping("{id}/image")
//    public ResponseEntity<AppUser> setUserImage(
//        @PathVariable long id,
//        @Valid @RequestBody UserImageRequestDTO imageDto
//    ) {
//        return new ResponseEntity<>(userService.setUserImage(id, imageDto), HttpStatus.OK);
//    }

    /**
     * This function sets the password of the user to the given new password
     *
     * @param id          The id of the user to be updated
     * @param passwordDto This is the object that contains the current and new passwords.
     * @return A ResponseEntity with the updated AppUser object and HttpStatus.
     */
    @PatchMapping(ENDPOINT_POSTFIX_USER_PASSWORD)
    public ResponseEntity<AppUserResponseDTO> setUserPassword(
            @PathVariable(PARAM_ID) long id,
            @Valid @RequestBody UserPasswordRequestDTO passwordDto
    ) {
        return ResponseEntity.ok(userService.setUserPassword(id, passwordDto));
    }
}
