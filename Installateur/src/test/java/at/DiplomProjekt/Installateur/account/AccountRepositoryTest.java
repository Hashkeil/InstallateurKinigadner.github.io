package at.DiplomProjekt.Installateur.account;

import at.DiplomProjekt.Installateur.TestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Test
    void ensureSavedAccount(){

        Account accountJohn = TestFixtures.createAccountForUserJohn();

        Account savedAccount = accountRepository.saveAndFlush(accountJohn);


        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();

        assumeThat(accountRepository).isNotNull();
        assumeThat(accountRepository.findAll()).isNotEmpty();


    }

}