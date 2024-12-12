package at.DiplomProjekt.Installateur.customer;

import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.customer.dto.CustomerFormRequest;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        // Setup SecurityContext to return our mock Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void createCustomer_WithValidData_ShouldCreateCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setEmail("customer@example.com");
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class); // Mocking CustomUserDetails
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // When
        Customer createdCustomer = customerService.createCustomer(customer);

        // Then
        assertNotNull(createdCustomer, "Customer should not be null");
        assertEquals("customer@example.com", createdCustomer.getEmail(), "Email should match");
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void findCustomerById_ExistingId_ShouldReturnCustomer() {
        // Given
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        Customer foundCustomer = customerService.findCustomerById(customerId);

        // Then
        assertNotNull(foundCustomer, "Customer should be found");
        assertEquals(customerId, foundCustomer.getId(), "Customer ID should match");
    }

    @Test
    void findCustomerById_NonExistingId_ShouldThrowEntityNotFoundException() {
        // Given
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> customerService.findCustomerById(customerId),
                "Should throw EntityNotFoundException when customer ID does not exist");
    }

    @Test
    void createCustomer_ShouldSetAccountFromAuthenticatedUser() {
        Customer customer = new Customer();
        customer.setEmail("customer@example.com");

        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        User user = new User();
        Account account = new Account();
        user.setAccount(account);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(customUserDetails.getUser()).thenReturn(user);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);

    }
    @Test
    void findOrCreateCustomer_ExistingCustomer_ShouldReturnExisting() {
        CustomerFormRequest customerModel = new CustomerFormRequest();
        customerModel.setEmail("existing@example.com");
        customerModel.setPhone("123456789");

        Customer existingCustomer = new Customer();
        when(customerRepository.findByEmailAndPhone(customerModel.getEmail(), customerModel.getPhone()))
                .thenReturn(Optional.of(existingCustomer));

        Customer resultCustomer = customerService.findOrCreateCustomer(customerModel);

        assertNotNull(resultCustomer);
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void findOrCreateCustomer_NonExistingCustomer_ShouldCreateNew() {
        CustomerFormRequest customerModel = new CustomerFormRequest();
        customerModel.setEmail("new@example.com");
        customerModel.setPhone("987654321");
        customerModel.setFirstName("First");
        customerModel.setLastName("Last");
        customerModel.setCompanyName("Company");

        when(customerRepository.findByEmailAndPhone(customerModel.getEmail(), customerModel.getPhone()))
                .thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer resultCustomer = customerService.findOrCreateCustomer(customerModel);

        assertNotNull(resultCustomer);
        assertEquals(customerModel.getEmail(), resultCustomer.getEmail());
        assertEquals(customerModel.getFirstName(), resultCustomer.getFirstName());
        assertEquals(customerModel.getLastName(), resultCustomer.getLastName());
        assertEquals(customerModel.getCompanyName(), resultCustomer.getCompanyName());
    }

    @Test
    void findCustomerByEmail_ShouldReturnCustomer() {
        String email = "customer@example.com";
        Customer customer = new Customer();
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findCustomerByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void findCustomerByPhone_ShouldReturnCustomer() {
        String phone = "123456789";
        Customer customer = new Customer();
        when(customerRepository.findByPhone(phone)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findCustomerByPhone(phone);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }





}
