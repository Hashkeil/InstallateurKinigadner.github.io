package at.DiplomProjekt.Installateur.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAccountByEmail_ExistingEmail() {
        String email = "test@example.com";
        Account expectedAccount = new Account(); // Assuming Account class has a no-arg constructor
        expectedAccount.setEmail(email);

        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(expectedAccount));

        Optional<Account> result = accountService.findAccountByEmail(email);

        assertTrue(result.isPresent(), "Account should be found");
        assertEquals(email, result.get().getEmail(), "Email should match");
    }


    @Test
    void findAccountByEmail_NonExistingEmail() {
        String email = "nonexisting@example.com";
        when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Account> result = accountService.findAccountByEmail(email);

        assertFalse(result.isPresent(), "Account should not be found");
    }


    @Test
    void createAccount_Success() {
        Account newAccount = new Account(); // Adjust as per your Account class constructor
        newAccount.setEmail("new@example.com");

        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        Account savedAccount = accountService.createAccount(newAccount);

        assertNotNull(savedAccount, "Saved account should not be null");
        assertEquals(newAccount.getEmail(), savedAccount.getEmail(), "Emails should match");

        assumeThat(accountRepository).isNotNull();
      //  assumeThat(accountRepository.findAll()).isNotEmpty();
    }
}
