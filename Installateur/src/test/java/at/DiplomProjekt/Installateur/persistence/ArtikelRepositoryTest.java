package at.DiplomProjekt.Installateur.persistence;
/*
import at.DiplomProjekt.Installateur.TestFixtures;
import at.DiplomProjekt.Installateur.entity.Artikel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
class ArtikelRepositoryTest {

    @Autowired
    private ArtikelRepository artikelRepository;



    @Test
    void  ensureSaveandReReadWorks(){


        var artikel = TestFixtures.artikel1();

        assumeThat(artikelRepository).isNotNull();
        artikelRepository.save(artikel);
        assumeThat(artikelRepository.findAll()).isNotEmpty();


        artikelRepository.save(artikel);

        // Then
        List<Artikel> savedArtikels = artikelRepository.findAll();
        assertThat(savedArtikels).isNotEmpty().contains(artikel);

        Artikel retrievedArtikel = artikelRepository.findByArtikelName("Schell Eckventil ");
        assertThat(retrievedArtikel).isEqualTo(artikel);

    }

}

 */