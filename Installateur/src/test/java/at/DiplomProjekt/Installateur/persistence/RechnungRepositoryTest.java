package at.DiplomProjekt.Installateur.persistence;
/*
import at.DiplomProjekt.Installateur.TestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static at.DiplomProjekt.Installateur.TestFixtures.am;
import static at.DiplomProjekt.Installateur.TestFixtures.mo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RechnungRepositoryTest {

    @Autowired
    private RechnungRepository rechnungRepository;


    @Test
    void ensureSaveAndReadWorks() {


        var kunde = mo();

        var mitarbeiter = am();

        var rechnung = TestFixtures.rechnung();


        var savedRechnung = rechnungRepository.saveAndFlush(rechnung);
        assumeThat(rechnungRepository.findAll()).isNotEmpty();

        assertNotNull(savedRechnung);
        assertNotNull(savedRechnung.getId());
        assertThat(savedRechnung).isNotNull().isSameAs(rechnung);
        assertThat(savedRechnung.getId()).isNotNull();
    }
}




 */