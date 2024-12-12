package at.DiplomProjekt.Installateur.security;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private  RoleRepository roleRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
                       UserRepository userRepository, AuthorityService authorityService,RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User createUser(UserFormModel userModel) throws EmailExistsException, RoleNotFoundException {

        if(emailExists(userModel.getEmail())) {throw new EmailExistsException("There is already an account using that email address: " + userModel.getEmail());}

        String encodedPassword = passwordEncoder.encode(userModel.getConfirmPassword());

        String userRole = "ADMIN";
        if(userModel.getAdmin()) {userRole = "ADMIN";}

        Account account = new Account();
        account.setEmail(userModel.getEmail());
        account.setActive(true);

        accountRepository.save(account);


        User user = new User();
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());


        Role role = new Role("ADMIN");
        roleRepository.save(role);
        user.setRoles(Collections.singleton(role));
        user.setAccount(account);
        account.getUsers().add(user);
        userRepository.save(user);

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User updateUser(User updatedUser) throws EmailExistsException {
        // Check if the user exists
        Optional<User> optionalUser = userRepository.findByEmail(updatedUser.getEmail());
        if (optionalUser.isEmpty()) {
            throw new EmailExistsException("User not found with Email: " + updatedUser.getEmail());
        }

        User existingUser = optionalUser.get();

        // Retrieve the account ID associated with the user
        Account accountId = existingUser.getAccount();

        // Set the account ID in the updated user object
        updatedUser.setAccount(accountId);

        // Update user details
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEnabled(updatedUser.isEnabled());


        existingUser.setRoles(updatedUser.getRoles());
        existingUser.setProducts(updatedUser.getProducts());
        existingUser.setInvoices(updatedUser.getInvoices());
        existingUser.setAccount(updatedUser.getAccount());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
        }
    }

    @Transactional
    public User updateUserSettings(UserFormModel updatedSettings) {
        // Retrieve the current user from the security context
        CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUser.getUser();

        // Update user settings
        user.setFirstName(updatedSettings.getFirstName());
        user.setLastName(updatedSettings.getLastName());
        user.setEmail(updatedSettings.getEmail());

        return userRepository.save(user);
    }


    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}