package at.DiplomProjekt.Installateur.security;

import at.DiplomProjekt.Installateur.TestFixtures;
import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private AccountRepository accountRepository;


    @BeforeEach
    void setUp(){
/*
        Privilege privilege = new Privilege();
        privilege.setName("READ_WRITE");
        Privilege savedPrivilege = privilegeRepository.save(privilege);

        Role  role = new Role();
        role.setName("ROLE_ADMIN");
        role.setPrivileges(Collections.singleton(savedPrivilege));


 */

        Privilege privilege = TestFixtures.privilege();
        Role role = TestFixtures.role();

    }

    @Test
    void  ensureSaveandReReadWorks(){


        Account accountJohn = TestFixtures.createAccountForUserJohn();
        accountRepository.save(accountJohn);
        User userJohn = TestFixtures.createUserJohn(accountJohn);

        User savedUser = userRepository.saveAndFlush(userJohn);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();

        assumeThat(userRepository).isNotNull();
        assumeThat(userRepository.findAll()).isNotEmpty();

    }

}

