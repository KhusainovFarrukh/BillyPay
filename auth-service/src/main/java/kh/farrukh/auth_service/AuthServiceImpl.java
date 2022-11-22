package kh.farrukh.auth_service;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import feign.FeignException;
import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.common.exceptions.exceptions.token_exceptions.*;
import kh.farrukh.common.security.TokenProvider;
import kh.farrukh.common.security.TokenResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.LoginRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.LoginResponseDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterRequestDTO;
import kh.farrukh.feign_clients.auth.payloads.RegisterResponseDTO;
import kh.farrukh.feign_clients.user.UserClient;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        AppUserResponseDTO appUserResponseDTO = userClient.createUser(AuthMappers.toAppUserRequestDTO(registerRequestDTO));
        TokenResponseDTO tokenResponseDTO = tokenProvider.generateTokens(
                appUserResponseDTO.getPhoneNumber(), appUserResponseDTO.getRole().getPermissions()
        );
        return AuthMappers.toRegisterResponseDTO(appUserResponseDTO, tokenResponseDTO);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            AppUserResponseDTO appUserResponseDTO = userClient.searchUserByUsername(loginRequestDTO.getPhoneNumber());
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), appUserResponseDTO.getEncodedPassword())) {
                // TODO: 11/21/22 custom exception
                throw new RuntimeException("Wrong password");
            }
            return AuthMappers.toLoginResponseDTO(tokenProvider.generateTokens(
                    appUserResponseDTO.getPhoneNumber(), appUserResponseDTO.getRole().getPermissions()
            ));
        } catch (FeignException.ServiceUnavailable | FeignException.NotFound e) {
            // TODO: 11/21/22 custom exception
            throw new RuntimeException("Wrong phone number");
        }
    }

    @Override
    public TokenResponseDTO refreshToken(String refreshToken) {
        try {
            DecodedJWT decodedJWT = tokenProvider.validateToken(refreshToken, true);
            String phoneNumber = decodedJWT.getSubject();
            try {
                AppUserResponseDTO user = userClient.searchUserByUsername(phoneNumber);
                return tokenProvider.generateAccessToken(user.getPhoneNumber(), user.getRole().getPermissions());
            } catch (FeignException.ServiceUnavailable | FeignException.NotFound e) {
                throw new ResourceNotFoundException("User", "phone number", phoneNumber);
            }
        } catch (AlgorithmMismatchException exception) {
            throw new WrongTypeTokenException();
        } catch (SignatureVerificationException exception) {
            throw new InvalidSignatureTokenException();
        } catch (TokenExpiredException exception) {
            throw new ExpiredTokenException();
        } catch (InvalidClaimException exception) {
            throw new InvalidRoleTokenException();
            // TODO: 11/21/22 implement
//        } catch (MissingTokenException exception) {
//            throw new MissingTokenException();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new UnknownTokenException();
        }
    }
}
