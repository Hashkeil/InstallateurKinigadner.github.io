package at.DiplomProjekt.Installateur.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UserRepository userRepository;

    private AuthorityService authorityService;

    public CustomUserDetailsService(UserRepository userRepository, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }


@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    logger.info("Running the loadUserByUsername (CustomUserDetailsService implementation)");

    final Optional<User> optionalCustomUser = this.userRepository.findByEmail(email);
    logger.info("Loading the UserDetails");

    User user = optionalCustomUser.orElseThrow(() -> new UsernameNotFoundException(email));

    logger.info("User loaded: " + user);

    CustomUserDetails userDetails = new CustomUserDetails(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword(),
            user,
            true,
            true,
            true,
            true,
            getAuthorities(user)
    );

    logger.info("CustomUserDetails created: " + userDetails);

    return userDetails;
}


    private static Collection<? extends  GrantedAuthority> getAuthorities(User user) {
        logger.info("getAuthorities for user email: " + user.getEmail());

        // new
        Collection<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().stream().map(privilege -> {
                return new SimpleGrantedAuthority(privilege.getName());
            }).forEach(authorities::add);
        });
        return authorities;
    }


}


