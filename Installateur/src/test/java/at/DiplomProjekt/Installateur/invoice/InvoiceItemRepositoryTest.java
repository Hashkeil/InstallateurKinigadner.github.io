package at.DiplomProjekt.Installateur.invoice;

import at.DiplomProjekt.Installateur.TestFixtures;
import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.AccountRepository;
import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.customer.CustomerRepository;
import at.DiplomProjekt.Installateur.product.Product;
import at.DiplomProjekt.Installateur.product.ProductRepository;
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
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest

class InvoiceItemRepositoryTest {


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
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Privilege privilege = TestFixtures.privilege();
        Role role = TestFixtures.role();
    }


    @Test
    void testSavedInvoiveItem(){

        Account accountForAlice = createAccountForUserAlice();
        accountRepository.save(accountForAlice);
        User userAlice = createUserAlice(accountForAlice);
        userRepository.save(userAlice);
        Customer customerAnna = createCustomerAnna();
        customerRepository.save(customerAnna);
        Invoice invoiceForAlice = createInvoiceForAlice(userAlice, customerAnna);
        invoiceRepository.save(invoiceForAlice);
        Product product1= product1();
        productRepository.save(product1);



        InvoiceItem invoiceItem = createInvoiceItemForAlice(product1,invoiceForAlice);
        InvoiceItem savedInvoiceItem = invoiceItemRepository.saveAndFlush(invoiceItem);

        assertThat(savedInvoiceItem).isNotNull();
        assertThat(savedInvoiceItem.getId()).isNotNull();
        assumeThat(invoiceItemRepository).isNotNull();
        assumeThat(invoiceItemRepository.findAll()).isNotEmpty();

    }

}