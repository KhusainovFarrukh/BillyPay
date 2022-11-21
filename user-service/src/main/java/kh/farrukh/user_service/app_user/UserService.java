package kh.farrukh.user_service.app_user;

import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import kh.farrukh.feign_clients.user.payloads.UserPasswordRequestDTO;
import kh.farrukh.feign_clients.user.payloads.UserRoleRequestDTO;

/**
 * A base interface for service of User endpoints
 * <p>
 * Methods implemented in UserServiceImpl
 */
public interface UserService {

    PagingResponse<AppUserResponseDTO> getUsers(int page, int pageSize);

    AppUserResponseDTO getUserById(Long id);

    AppUserResponseDTO createUser(AppUserRequestDTO userRequestDTO);

    AppUserResponseDTO updateUser(long id, AppUserRequestDTO userRequestDTO);

    void deleteUser(long id);

    AppUserResponseDTO setUserRole(long id, UserRoleRequestDTO userRoleRequestDTO);

//    AppUser setUserImage(long id, UserImageRequestDTO imageDto);

    AppUserResponseDTO setUserPassword(long id, UserPasswordRequestDTO userPasswordRequestDTO);
}
