
/*
import at.DiplomProjekt.Installateur.controller.KundeController;
import at.DiplomProjekt.Installateur.entity.Kunde;
import at.DiplomProjekt.Installateur.service.KundeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KundeControllerTest {

    @Mock
    private KundeService kundeService;

    @InjectMocks
    private KundeController kundeController;

    @Test
    public void testListKunden() {
        Model model = mock(Model.class);

        List<Kunde> expectedKunden = mock(List.class);
        when(kundeService.getAllKunden()).thenReturn(expectedKunden);

        String viewName = kundeController.listKunden(model);

        assertEquals("artikel/k_list", viewName);
        verify(model, times(1)).addAttribute("kunden", expectedKunden);
    }

    @Test
    public void testNewKundeForm() {
        Model model = mock(Model.class);

        String viewName = kundeController.newKundeForm(model);

        assertEquals("artikel/k_new", viewName);
        verify(model, times(1)).addAttribute("kunde", new Kunde());
    }

    @Test
    public void testCreateKunde() {
        Kunde kundeToCreate = new Kunde();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        Kunde createdKunde = new Kunde();
        when(kundeService.createKunde(kundeToCreate)).thenReturn(createdKunde);

        String viewName = kundeController.createKunde(kundeToCreate, bindingResult, redirectAttributes);

        assertEquals("redirect:/kunde/list", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Kunde successfully created with ID: " + createdKunde.getId());
    }

    // Additional tests for other methods

}


 */