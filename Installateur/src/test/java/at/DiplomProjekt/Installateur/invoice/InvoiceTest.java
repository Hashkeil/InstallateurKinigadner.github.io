package at.DiplomProjekt.Installateur.invoice;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.AccountRepository;
import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.customer.CustomerRepository;
import at.DiplomProjekt.Installateur.security.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static at.DiplomProjekt.Installateur.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;


@DataJpaTest
@Transactional
class InvoiceTest {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @BeforeEach
    void setUp() {
        Privilege privilege = new Privilege();
        privilege.setName("READ_WRITE");
        Privilege savedPrivilege = privilegeRepository.save(privilege);

        Role role = new Role();
        role.setName("ROLE_USER");
        role.setPrivileges(Collections.singleton(savedPrivilege));
    }

    @Test
    void saveInvoiceTest() {

        Account accountForAlice = createAccountForUserAlice();
        accountRepository.save(accountForAlice);
        User userAlice = createUserAlice(accountForAlice);
        userRepository.save(userAlice);


        Customer customerAnna = createCustomerAnna();
        customerRepository.save(customerAnna);

        Invoice invoiceForAlice = createInvoiceForAlice(userAlice, customerAnna);
        Invoice savedInvoice = invoiceRepository.saveAndFlush(invoiceForAlice);





        assertThat(savedInvoice).isNotNull();
        assertThat(savedInvoice.getId()).isNotNull();
        // TODO
        // assertThat(savedInvoice.getUser()).isEqualTo(createUserAlice(createAccountForUserAlice()));
        // assertThat(savedInvoice.getCustomer()).isEqualTo(createCustomerAnna());

        assumeThat(invoiceRepository).isNotNull();
        assumeThat(invoiceRepository.findAll()).isNotEmpty();
    }
}