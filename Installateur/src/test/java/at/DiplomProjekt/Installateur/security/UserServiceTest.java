package at.DiplomProjekt.Installateur.security;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
     //   userService.passwordEncoder = passwordEncoder; // Manually inject the PasswordEncoder
    }

    @Test
    void createUser_WithNonExistentEmail_ShouldCreateUser() throws EmailExistsException, RoleNotFoundException {
        // Given
        UserFormModel userModel = new UserFormModel();
        userModel.setEmail("newuser@example.com");
        userModel.setConfirmPassword("password");
        userModel.setAdmin(false);
        userModel.setFirstName("First");
        userModel.setLastName("Last");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(authorityService.findRoleByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(userModel.getConfirmPassword())).thenReturn("encodedPassword");

        // When
        User result = userService.createUser(userModel);

        // Then
        assertNotNull(result, "User should be created");
        assertEquals(userModel.getEmail(), result.getEmail(), "Emails should match");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_WithEmailAlreadyExists_ShouldThrowEmailExistsException() {
        // Given
        UserFormModel userModel = new UserFormModel();
        userModel.setEmail("existinguser@example.com");

        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(new User()));

        // When & Then
        assertThrows(EmailExistsException.class, () -> userService.createUser(userModel),
                "Should throw EmailExistsException when email already exists");
    }

    @Test
    void createUser_WithNonExistentRole_ShouldThrowRoleNotFoundException() {
        // Given
        UserFormModel userModel = new UserFormModel();
        userModel.setEmail("newuser@example.com");
        userModel.setAdmin(true); // Assuming this should assign ROLE_ADMIN

        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(authorityService.findRoleByName("ROLE_ADMIN")).thenReturn(Optional.empty()); // No ROLE_ADMIN found

        // When & Then
        assertThrows(RoleNotFoundException.class, () -> userService.createUser(userModel),
                "Should throw RoleNotFoundException when role does not exist");
    }




    @Test
    void deleteUser_ValidUserId_UserDeleted() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_InvalidUserId_NoUserDeleted() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(0)).deleteById(userId);
    }
}
