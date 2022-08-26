package kh.farrukh.user;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.user.payloads.AppUserRequestDTO;
import kh.farrukh.user.payloads.UserImageRequestDTO;
import kh.farrukh.user.payloads.UserPasswordRequestDTO;
import kh.farrukh.user.payloads.UserRoleRequestDTO;

/**
 * A base interface for service of User endpoints
 * <p>
 * Methods implemented in UserServiceImpl
 */
public interface UserService {

    PagingResponse<AppUser> getUsers(int page, int pageSize);

    AppUser getUserById(Long id);

    AppUser createUser(AppUserRequestDTO userRequestDTO);

    AppUser updateUser(long id, AppUserRequestDTO appUserDto);

    void deleteUser(long id);

    AppUser setUserRole(long id, UserRoleRequestDTO roleDto);

//    AppUser setUserImage(long id, UserImageRequestDTO imageDto);

    AppUser setUserPassword(long id, UserPasswordRequestDTO passwordDto);
}
