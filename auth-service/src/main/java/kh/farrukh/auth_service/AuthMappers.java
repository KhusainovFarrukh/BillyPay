package kh.farrukh.auth_service;

import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserRequestDTO;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import org.springframework.beans.BeanUtils;

public class AuthMappers {

    public static AppUserRequestDTO toAppUserRequestDTO(RegisterRequestDTO registerRequestDTO) {
        AppUserRequestDTO appUserRequestDTO = new AppUserRequestDTO();
        BeanUtils.copyProperties(registerRequestDTO, appUserRequestDTO);
        return appUserRequestDTO;
    }

    public static RegisterResponseDTO toRegisterResponseDTO(AppUserResponseDTO appUserResponseDTO) {
        return new RegisterResponseDTO(appUserResponseDTO);
    }
}
