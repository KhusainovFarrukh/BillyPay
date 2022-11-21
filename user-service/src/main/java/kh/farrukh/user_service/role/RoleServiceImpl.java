package kh.farrukh.user_service.role;

import kh.farrukh.common.exceptions.exceptions.DuplicateResourceException;
import kh.farrukh.common.exceptions.exceptions.ResourceNotFoundException;
import kh.farrukh.common.paging.PagingResponse;
import kh.farrukh.feign_clients.role.payloads.RoleRequestDTO;
import kh.farrukh.feign_clients.role.payloads.RoleResponseDTO;
import kh.farrukh.user_service.app_user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static kh.farrukh.common.paging.PageChecker.checkPageNumber;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public PagingResponse<RoleResponseDTO> getRoles(int page, int pageSize) {
        checkPageNumber(page);
        return new PagingResponse<>(roleRepository.findAll(
                PageRequest.of(page - 1, pageSize)
        ).map(RoleMappers::toRoleResponseDTO));
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(RoleMappers::toRoleResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        if (roleRepository.existsByTitle(roleRequestDTO.getTitle())) {
            throw new DuplicateResourceException("Role", "title", roleRequestDTO.getTitle());
        }

        return RoleMappers.toRoleResponseDTO(roleRepository.save(RoleMappers.toRole(roleRequestDTO)));
    }

    @Override
    public RoleResponseDTO updateRole(long id, RoleRequestDTO roleRequestDTO) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

//        if (CurrentUserUtils.isAdminOrAuthor(id, userRepository)) {

        if (!roleRequestDTO.getTitle().equals(existingRole.getTitle()) &&
                roleRepository.existsByTitle(roleRequestDTO.getTitle())) {
            throw new DuplicateResourceException("Role", "title", roleRequestDTO.getTitle());
        }

        if (existingRole.getIsDefault() &&
                !roleRequestDTO.getIsDefault() &&
                roleRepository.countByIsDefaultIsTrue() <= 1) {
            throw new DefaultRoleDeletionException();
        }

        existingRole.setTitle(roleRequestDTO.getTitle());
        existingRole.setPermissions(roleRequestDTO.getPermissions());
        existingRole.setIsDefault(roleRequestDTO.getIsDefault());

        return RoleMappers.toRoleResponseDTO(roleRepository.save(existingRole));
//        } else {
//            throw new NotEnoughPermissionException();
//        }
    }

    @Override
    public void deleteRole(long id) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (existingRole.getIsDefault() && roleRepository.countByIsDefaultIsTrue() <= 1) {
            throw new DefaultRoleDeletionException();
        }

        if (existingRole.getUsers() != null && !existingRole.getUsers().isEmpty()) {
            Role defaultRole = roleRepository.findByIsDefaultIsTrueAndIdIsNot(existingRole.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "isDefault", true));
            existingRole.getUsers().forEach(user -> {
                user.setRole(defaultRole);
                userRepository.saveAndFlush(user);
            });
        }

        roleRepository.deleteById(id);
    }
}
