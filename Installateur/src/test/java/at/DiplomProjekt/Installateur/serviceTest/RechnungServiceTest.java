package at.DiplomProjekt.Installateur.serviceTest;
/*
import at.DiplomProjekt.Installateur.entity.Rechnung;
import at.DiplomProjekt.Installateur.persistence.RechnungRepository;
import at.DiplomProjekt.Installateur.service.RechnungService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RechnungServiceTest {

    @Mock
    private RechnungRepository rechnungRepository;

    @InjectMocks
    private RechnungService rechnungService;

    @Test
    public void testGetAllRechnungen() {
        List<Rechnung> expectedRechnungen = new ArrayList<>();
        when(rechnungRepository.findAll()).thenReturn(expectedRechnungen);

        List<Rechnung> actualRechnungen = rechnungService.getAllRechnungs();

        assertEquals(expectedRechnungen, actualRechnungen);
        verify(rechnungRepository, times(1)).findAll();
    }

    @Test
    public void testGetRechnungById() {
        Long rechnungId = 1L;
        Rechnung expectedRechnung = new Rechnung();
        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(expectedRechnung));

        Optional<Rechnung> actualRechnung = rechnungService.getRechnungById(rechnungId);

        assertEquals(Optional.of(expectedRechnung), actualRechnung);
        verify(rechnungRepository, times(1)).findById(rechnungId);
    }

    @Test
    public void testCreateRechnung() {
        Rechnung rechnungToCreate = new Rechnung();
        Rechnung createdRechnung = new Rechnung();
        when(rechnungRepository.save(rechnungToCreate)).thenReturn(createdRechnung);

        Rechnung actualRechnung = rechnungService.createRechnung(rechnungToCreate);

        assertEquals(createdRechnung, actualRechnung);
        verify(rechnungRepository, times(1)).save(rechnungToCreate);
    }

    @Test
    public void testUpdateRechnung() {
        Long rechnungId = 1L;
        Rechnung updatedRechnung = new Rechnung();
        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(new Rechnung()));
        when(rechnungRepository.save(any())).thenReturn(updatedRechnung);

        Rechnung actualRechnung = rechnungService.updateRechnung(rechnungId, updatedRechnung);

        assertEquals(updatedRechnung, actualRechnung);
        verify(rechnungRepository, times(1)).findById(rechnungId);
        verify(rechnungRepository, times(1)).save(any());
    }



}


 */