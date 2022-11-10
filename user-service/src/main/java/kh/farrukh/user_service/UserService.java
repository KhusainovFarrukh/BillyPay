package kh.farrukh.user_service;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.user_service.payloads.AppUserRequestDTO;
import kh.farrukh.user_service.payloads.AppUserResponseDTO;
import kh.farrukh.user_service.payloads.UserPasswordRequestDTO;
import kh.farrukh.user_service.payloads.UserRoleRequestDTO;

/**
 * A base interface for service of User endpoints
 * <p>
 * Methods implemented in UserServiceImpl
 */
public interface UserService {

    PagingResponse<AppUserResponseDTO> getUsers(int page, int pageSize);

    AppUserResponseDTO getUserById(Long id);

    AppUserResponseDTO createUser(AppUserRequestDTO userRequestDTO);

    AppUserResponseDTO updateUser(long id, AppUserRequestDTO appUserDto);

    void deleteUser(long id);

    AppUserResponseDTO setUserRole(long id, UserRoleRequestDTO roleDto);

//    AppUser setUserImage(long id, UserImageRequestDTO imageDto);

    AppUserResponseDTO setUserPassword(long id, UserPasswordRequestDTO passwordDto);
}
