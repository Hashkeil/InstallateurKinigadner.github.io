package at.DiplomProjekt.Installateur.serviceTest;
/*
import at.DiplomProjekt.Installateur.entity.Artikel;
import at.DiplomProjekt.Installateur.persistence.ArtikelRepository;
import at.DiplomProjekt.Installateur.service.ArtikelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtikelServiceTest {

    @Mock
    private ArtikelRepository artikelRepository;

    @InjectMocks
    private ArtikelService artikelService;

    @Test
    public void testGetAllArtikel() {
        // Arrange
        Artikel artikel1 = new Artikel("Artikel1", 10.0, 5);
        Artikel artikel2 = new Artikel("Artikel2", 15.0, 8);
        when(artikelRepository.findAll()).thenReturn(Arrays.asList(artikel1, artikel2));

        // Act
        List<Artikel> result = artikelService.getAllArtikel();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getArtikelName()).isEqualTo("Artikel1");
        assertThat(result.get(1).getArtikelName()).isEqualTo("Artikel2");
    }

    @Test
    public void testGetArtikelById() {
        long artikelId = 1L;
        Artikel artikel = new Artikel("Artikel1", 10.0, 5);
        when(artikelRepository.findById(artikelId)).thenReturn(Optional.of(artikel));

        Optional<Artikel> result = artikelService.getArtikelById(artikelId);

        assertThat(result).isPresent();
        assertThat(result.get().getArtikelName()).isEqualTo("Artikel1");
    }

    @Test
    public void testCreateArtikel() {
        Artikel artikelToCreate = new Artikel("NewArtikel", 20.0, 3);
        when(artikelRepository.save(any())).thenReturn(artikelToCreate);

        Artikel createdArtikel = artikelService.createArtikel(artikelToCreate);

        assertThat(createdArtikel).isNotNull();
        assertThat(createdArtikel.getArtikelName()).isEqualTo("NewArtikel");
        verify(artikelRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateArtikel() {
        long artikelId = 1L;

        Artikel existingArtikel = new Artikel();
        when(artikelRepository.findById(artikelId)).thenReturn(Optional.of(existingArtikel));

        Artikel updatedArtikel = new Artikel("UpdatedArtikel", 18.0, 10);

        Artikel result = artikelService.updateArtikel(artikelId, updatedArtikel);

        assertThat(result).isNotNull();
        assertThat(result.getArtikelName()).isEqualTo("UpdatedArtikel");
        assertThat(result.getPreis()).isEqualTo(18.0);
        assertThat(result.getQuantity()).isEqualTo(10);
        verify(artikelRepository, times(1)).save(any());
    }


    @Test
    public void testDeleteArtikel() {
        long artikelId = 1L;

        artikelService.deleteArtikel(artikelId);

        verify(artikelRepository, times(1)).deleteById(artikelId);
    }

    @Test
    public void testSearchArtikel() {
        String name = "Artikel";
        Long id = 1L;
        when(artikelRepository.findArtikelByIdOrArtikelName(id, name)).thenReturn(Arrays.asList(
                new Artikel("Artikel1", 10.0, 5),
                new Artikel("Artikel2", 15.0, 8)
        ));

        List<Artikel> result = artikelService.searchArtikel(name, id);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getArtikelName()).isEqualTo("Artikel1");
        assertThat(result.get(1).getArtikelName()).isEqualTo("Artikel2");
    }



}


 */