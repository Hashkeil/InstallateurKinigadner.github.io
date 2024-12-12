package at.DiplomProjekt.Installateur.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class AuthorityService {

    private RoleRepository roleRepository;

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public AuthorityService(
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository
    ) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public Optional<Role> findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

}
