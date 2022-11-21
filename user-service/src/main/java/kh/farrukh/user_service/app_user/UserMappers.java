package kh.farrukh.user_service.app_user;

import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import kh.farrukh.user_service.role.RoleMappers;
import kh.farrukh.user_service.role.RoleRepository;
import org.springframework.beans.BeanUtils;

public class UserMappers {

    public static AppUserResponseDTO toAppUserResponseDTO(AppUser appUser) {
        AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO();
        BeanUtils.copyProperties(appUser, appUserResponseDTO);
        appUserResponseDTO.setRole(RoleMappers.toRoleResponseDTO(appUser.getRole()));
        return appUserResponseDTO;
    }

    public static AppUser toAppUser(AppUserRequestDTO appUserRequestDTO, RoleRepository roleRepository) {
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(appUserRequestDTO, appUser);
        appUser.setRole(roleRepository.findFirstByIsDefaultIsTrue()
                .orElseThrow(() -> new ResourceNotFoundException("Role", "isDefault", true)));
        return appUser;
    }
}
