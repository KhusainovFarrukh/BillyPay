package kh.farrukh.user;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.user.payloads.AppUserRequestDTO;
import kh.farrukh.user.payloads.AppUserResponseDTO;
import kh.farrukh.user.payloads.UserPasswordRequestDTO;
import kh.farrukh.user.payloads.UserRoleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static kh.farrukh.user.Constants.ENDPOINT_USER;

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
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize
    ) {
        return new ResponseEntity<>(userService.getUsers(page, pageSize), HttpStatus.OK);
    }

    /**
     * It returns a user by id.
     *
     * @param id The id of the user you want to get.
     * @return A ResponseEntity with found AppUser.
     */
    @GetMapping("{id}")
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> createUser(@RequestBody AppUserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.OK);
    }

    /**
     * This function updates a user.
     *
     * @param id         The id of the user to update
     * @param appUserDto The user values that we want to update.
     * @return A ResponseEntity with the updated AppUser object and HttpStatus.
     */
    @PutMapping("{id}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable long id, @Valid @RequestBody AppUserRequestDTO appUserDto) {
        return new ResponseEntity<>(userService.updateUser(id, appUserDto), HttpStatus.OK);
    }

    /**
     * This function deletes a user from a language
     *
     * @param id The id of the user to delete
     * @return A ResponseEntity with HttpStatus.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This function sets the role of the user with the given id,
     * to the role given in the request body.
     *
     * @param id      The id of the user to be updated
     * @param roleDto This is the object that contains the role that we want to set the user to.
     * @return A ResponseEntity with the updated AppUser object and HttpStatus.
     */
    @PatchMapping("{id}/role")
    public ResponseEntity<AppUserResponseDTO> setUserRole(
            @PathVariable long id,
            @Valid @RequestBody UserRoleRequestDTO roleDto
    ) {
        return new ResponseEntity<>(userService.setUserRole(id, roleDto), HttpStatus.OK);
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
    @PatchMapping("{id}/password")
    public ResponseEntity<AppUserResponseDTO> setUserPassword(
            @PathVariable long id,
            @Valid @RequestBody UserPasswordRequestDTO passwordDto
    ) {
        return new ResponseEntity<>(userService.setUserPassword(id, passwordDto), HttpStatus.OK);
    }
}
