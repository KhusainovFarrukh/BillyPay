package kh.farrukh.user_service;

import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import kh.farrukh.feign_clients.user.payloads.UserRoleDTO;
import org.springframework.beans.BeanUtils;

public class UserMappers {

    public static UserRoleDTO toUserRoleDTO(UserRole userRole) {
        return UserRoleDTO.valueOf(userRole.name());
    }

    public static UserRole toUserRole(UserRoleDTO userRoleDTO) {
        return UserRole.valueOf(userRoleDTO.name());
    }

    public static AppUserResponseDTO toAppUserResponseDTO(AppUser appUser) {
        AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO();
        BeanUtils.copyProperties(appUser, appUserResponseDTO);
        appUserResponseDTO.setRole(toUserRoleDTO(appUser.getRole()));
        return appUserResponseDTO;
    }

    public static AppUser toAppUser(AppUserRequestDTO appUserRequestDTO) {
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(appUserRequestDTO, appUser);
        appUser.setRole(toUserRole(UserRoleDTO.USER));
        return appUser;
    }
}
