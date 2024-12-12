/*
package at.DiplomProjekt.Installateur.Controller;

import at.DiplomProjekt.Installateur.controller.AuftragController;
import at.DiplomProjekt.Installateur.entity.Auftrag;
import at.DiplomProjekt.Installateur.service.AuftragService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuftragControllerTest {

    @Mock
    private AuftragService auftragService;

    @InjectMocks
    private AuftragController auftragController;

    @Test
    public void testSearchAuftrag() {
        Model model = mock(Model.class);
        String auftragsId = "1";

        Auftrag expectedAuftrag = new Auftrag();
        when(auftragService.getAuftragById(Long.valueOf(auftragsId))).thenReturn(Optional.of(expectedAuftrag));

        String viewName = auftragController.searchAuftrag(model, Long.valueOf(auftragsId));

        assertEquals("auftrag", viewName);
        verify(model, times(1)).addAttribute("auftragList", Optional.of(expectedAuftrag));
    }

    @Test
    public void testCreateAuftrag() {
        Auftrag auftragToCreate = new Auftrag();

        Auftrag createdAuftrag = new Auftrag();
        when(auftragService.createAuftrag(auftragToCreate)).thenReturn(createdAuftrag);

        Auftrag actualAuftrag = auftragController.createAuftrag(auftragToCreate);

        assertEquals(createdAuftrag, actualAuftrag);
        verify(auftragService, times(1)).createAuftrag(auftragToCreate);
    }

    // Additional tests for other methods

}


 */