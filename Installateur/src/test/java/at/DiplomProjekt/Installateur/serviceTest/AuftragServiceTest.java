package at.DiplomProjekt.Installateur.serviceTest;
/*
import at.DiplomProjekt.Installateur.entity.Auftrag;
import at.DiplomProjekt.Installateur.persistence.AuftragRepository;
import at.DiplomProjekt.Installateur.service.AuftragService;
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
public class AuftragServiceTest {

    @Mock
    private AuftragRepository auftragRepository;

    @InjectMocks
    private AuftragService auftragService;

    @Test
    public void testGetAllAuftraege() {
        List<Auftrag> expectedAuftraege = new ArrayList<>();
        when(auftragRepository.findAll()).thenReturn(expectedAuftraege);

        List<Auftrag> actualAuftraege = auftragService.getAllAuftraege();

        assertEquals(expectedAuftraege, actualAuftraege);
        verify(auftragRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuftragById() {
        Long auftragId = 1L;
        Auftrag expectedAuftrag = new Auftrag();
        when(auftragRepository.findById(auftragId)).thenReturn(Optional.of(expectedAuftrag));

        Optional<Auftrag> actualAuftrag = auftragService.getAuftragById(auftragId);

        assertEquals(Optional.of(expectedAuftrag), actualAuftrag);
        verify(auftragRepository, times(1)).findById(auftragId);
    }

    @Test
    public void testCreateAuftrag() {
        Auftrag auftragToCreate = new Auftrag();
        Auftrag createdAuftrag = new Auftrag();
        when(auftragRepository.save(auftragToCreate)).thenReturn(createdAuftrag);

        Auftrag actualAuftrag = auftragService.createAuftrag(auftragToCreate);

        assertEquals(createdAuftrag, actualAuftrag);
        verify(auftragRepository, times(1)).save(auftragToCreate);
    }

    @Test
    public void testUpdateAuftrag() {
        Long auftragId = 1L;
        Auftrag updatedAuftrag = new Auftrag();
        when(auftragRepository.findById(auftragId)).thenReturn(Optional.of(new Auftrag()));
        when(auftragRepository.save(any())).thenReturn(updatedAuftrag);

        Auftrag actualAuftrag = auftragService.updateAuftrag(auftragId, updatedAuftrag);

        assertEquals(updatedAuftrag, actualAuftrag);
        verify(auftragRepository, times(1)).findById(auftragId);
        verify(auftragRepository, times(1)).save(any());
    }



}

 */
