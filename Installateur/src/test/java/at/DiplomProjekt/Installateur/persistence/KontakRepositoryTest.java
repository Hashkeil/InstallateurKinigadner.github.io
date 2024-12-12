package at.DiplomProjekt.Installateur.persistence;
/*
import at.DiplomProjekt.Installateur.entity.Mitarbeiter;
import at.DiplomProjekt.Installateur.entity.Kontakt;
import at.DiplomProjekt.Installateur.entity.MAJobs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class KontakRepositoryTest {


    @Autowired
    private KontakRepository kontakRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;



    @Test
    void  ensureSaveandReReadWorks(){
        Kontakt kontakt = Kontakt.builder()
                .firstName("alex")
                .lastName("Huber")
                .eMail("max@gmail.com")
                .beschreibung("PHD")
                .mobile(23567892)
                .adresse("SPengergasse 20")
                .plz(1050)
                .mitarbeiter(Mitarbeiter.builder()
                        .svn(3456782)
                        .birthDate(LocalDate.of(1990,12,2))
                        .jobBezeichnung(MAJobs.MEISTER)
                        .build())
                .build();

        mitarbeiterRepository.save(kontakt.getMitarbeiter());
        assumeThat(mitarbeiterRepository).isNotNull();
        assumeThat(kontakRepository).isNotNull();
        kontakRepository.save(kontakt);
        assumeThat(kontakRepository.findAll()).isNotEmpty();
        assumeThat(kontakRepository.findByFirstName("alex")).isEqualTo(kontakt);

    }


}

 */