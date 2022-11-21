package kh.farrukh.auth_service.security;

import feign.FeignException;
import kh.farrukh.feign_clients.user.UserClient;
import kh.farrukh.feign_clients.user.payloads.AppUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUserResponseDTO appUser = userClient.searchUserByUsername(username);
            return new User(
                    appUser.getPhoneNumber(),
                    appUser.getEncodedPassword(),
                    appUser.getRole().getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.name())).toList()
            );
        } catch (FeignException.ServiceUnavailable | FeignException.NotFound e) {
            throw new UsernameNotFoundException("User not found in the database");
        }
    }
}
