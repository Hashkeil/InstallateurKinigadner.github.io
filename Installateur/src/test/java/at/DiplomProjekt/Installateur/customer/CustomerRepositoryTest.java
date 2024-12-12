package at.DiplomProjekt.Installateur.customer;

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
class CustomerRepositoryTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void ensureSavedCustomer(){


        Customer customer = TestFixtures.createCustomerAnna();

        Customer savedCustomer = customerRepository.save(customer);


        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();

        assumeThat(customerRepository).isNotNull();
        assumeThat(customerRepository.findAll()).isNotEmpty();

    }
}