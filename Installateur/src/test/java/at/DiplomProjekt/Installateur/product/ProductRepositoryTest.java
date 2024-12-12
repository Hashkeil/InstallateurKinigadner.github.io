package at.DiplomProjekt.Installateur.product;

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
class ProductRepositoryTest {


    @Autowired
    private ProductRepository productRepository;

    @Test
    void ensureSavedProduct(){


        Product product = TestFixtures.product1();
        Product product1 = TestFixtures.product2();


        Product savedProduct = productRepository.saveAndFlush(product);
        Product savedProduct1= productRepository.saveAndFlush(product1);

        assumeThat(productRepository).isNotNull();
        assumeThat(productRepository.findAll()).isNotEmpty();

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct1).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct1.getId()).isNotNull();



    }
}