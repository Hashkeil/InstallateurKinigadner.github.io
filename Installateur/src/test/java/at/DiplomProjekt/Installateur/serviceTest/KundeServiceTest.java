package at.DiplomProjekt.Installateur.serviceTest;
/*
import at.DiplomProjekt.Installateur.entity.Kunde;
import at.DiplomProjekt.Installateur.persistence.KundenRepository;
import at.DiplomProjekt.Installateur.service.KundeService;
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
public class KundeServiceTest {

    @Mock
    private KundenRepository kundeRepository;

    @InjectMocks
    private KundeService kundeService;

    @Test
    public void testGetAllKunden() {
        List<Kunde> expectedKunden = new ArrayList<>();
        when(kundeRepository.findAll()).thenReturn(expectedKunden);

        List<Kunde> actualKunden = kundeService.getAllKunden();

        assertEquals(expectedKunden, actualKunden);
        verify(kundeRepository, times(1)).findAll();
    }

    @Test
    public void testGetKundeById() {
        Long kundeId = 1L;
        Kunde expectedKunde = new Kunde();
        when(kundeRepository.findById(kundeId)).thenReturn(Optional.of(expectedKunde));

        Optional<Kunde> actualKunde = kundeService.getKundeById(kundeId);

        assertEquals(Optional.of(expectedKunde), actualKunde);
        verify(kundeRepository, times(1)).findById(kundeId);
    }

    @Test
    public void testCreateKunde() {
        Kunde kundeToCreate = new Kunde();
        Kunde createdKunde = new Kunde();
        when(kundeRepository.save(kundeToCreate)).thenReturn(createdKunde);

        Kunde actualKunde = kundeService.createKunde(kundeToCreate);

        assertEquals(createdKunde, actualKunde);
        verify(kundeRepository, times(1)).save(kundeToCreate);
    }

    @Test
    public void testCreateOrUpdateArtikel() {
        Kunde kundeToUpdate = new Kunde();
        kundeService.createOrUpdateArtikel(kundeToUpdate);
        verify(kundeRepository, times(1)).save(kundeToUpdate);
    }

    @Test
    public void testDeleteKunde() {
        Long kundeId = 1L;
        kundeService.deleteKunde(kundeId);
        verify(kundeRepository, times(1)).deleteById(kundeId);
    }

    @Test
    public void testUpdateKunde() {
        Long kundeId = 1L;
        Kunde updatedKunde = new Kunde();
        when(kundeRepository.findById(kundeId)).thenReturn(Optional.of(new Kunde()));
        when(kundeRepository.save(any())).thenReturn(updatedKunde);

        Kunde actualKunde = kundeService.updateKunde(kundeId, updatedKunde);

        assertEquals(updatedKunde, actualKunde);
        verify(kundeRepository, times(1)).findById(kundeId);
        verify(kundeRepository, times(1)).save(any());
    }


}


 */