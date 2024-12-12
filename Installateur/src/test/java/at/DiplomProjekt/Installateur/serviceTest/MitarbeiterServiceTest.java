
package at.DiplomProjekt.Installateur.serviceTest;
/*

import at.DiplomProjekt.Installateur.entity.Mitarbeiter;
import at.DiplomProjekt.Installateur.persistence.MitarbeiterRepository;
import at.DiplomProjekt.Installateur.service.MitarbeiterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@DataJpaTest
public class MitarbeiterServiceTest {
    @Mock
    private MitarbeiterRepository mitarbeiterRepository;

    @InjectMocks
    private MitarbeiterService mitarbeiterService;

    @Test
    void findMitarbeiterById_ValidId_ReturnsMitarbeiter() {
        Long id = 1L;
        Mitarbeiter expectedMitarbeiter = new Mitarbeiter();
        when(mitarbeiterRepository.findById(id)).thenReturn(Optional.of(expectedMitarbeiter));

        Mitarbeiter result = mitarbeiterService.findMitarbeiterById(id);

        assertEquals(expectedMitarbeiter, result);
    }

    @Test
    void getAllMitarbeiters_ReturnsListOfMitarbeiters() {
        List<Mitarbeiter> expectedMitarbeiters = new ArrayList<>();
        when(mitarbeiterRepository.findAll()).thenReturn(expectedMitarbeiters);

        List<Mitarbeiter> result = mitarbeiterService.getAllMitarbeiters();

        assertEquals(expectedMitarbeiters, result);
    }

    @Test
    void getMitarbeiterById_ValidId_ReturnsOptionalMitarbeiter() {
        Long id = 1L;
        Mitarbeiter expectedMitarbeiter = new Mitarbeiter();
        when(mitarbeiterRepository.findById(id)).thenReturn(Optional.of(expectedMitarbeiter));

        Optional<Mitarbeiter> result = mitarbeiterService.getMitarbeiterById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedMitarbeiter, result.get());
    }


    @Test
    void updateMitarbeiter_ValidIdAndMitarbeiter_ReturnsUpdatedMitarbeiter() {
        Long id = 1L;
        Mitarbeiter existingMitarbeiter = new Mitarbeiter();
        Mitarbeiter updatedMitarbeiter = new Mitarbeiter();
        when(mitarbeiterRepository.findById(id)).thenReturn(Optional.of(existingMitarbeiter));
        when(mitarbeiterRepository.save(existingMitarbeiter)).thenReturn(updatedMitarbeiter);

        Mitarbeiter result = mitarbeiterService.updateMitarbeiter(id, updatedMitarbeiter);

        assertEquals(updatedMitarbeiter, result);
    }

    @Test
    void updateMitarbeiter_InvalidId_ThrowsRuntimeException() {
        Long id = 1L;
        Mitarbeiter updatedMitarbeiter = new Mitarbeiter();
        when(mitarbeiterRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> mitarbeiterService.updateMitarbeiter(id, updatedMitarbeiter));
    }

}


 */