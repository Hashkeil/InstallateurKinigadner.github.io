package at.DiplomProjekt.Installateur.serviceTest;
/*
import at.DiplomProjekt.Installateur.entity.TimeStamp;
import at.DiplomProjekt.Installateur.persistence.TimeStampRepository;
import at.DiplomProjekt.Installateur.service.TimeStampService;
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
public class TimeStampServiceTest {

    @Mock
    private TimeStampRepository timeStampRepository;

    @InjectMocks
    private TimeStampService timeStampService;

    @Test
    public void testGetAllTimeStamps() {
        List<TimeStamp> expectedTimeStamps = new ArrayList<>();
        when(timeStampRepository.findAll()).thenReturn(expectedTimeStamps);

        List<TimeStamp> actualTimeStamps = timeStampService.getAllTimeStamps();

        assertEquals(expectedTimeStamps, actualTimeStamps);
        verify(timeStampRepository, times(1)).findAll();
    }

    @Test
    public void testGetTimeStampById() {
        long timeStampId = 1;
        TimeStamp expectedTimeStamp = new TimeStamp();
        when(timeStampRepository.findById(timeStampId)).thenReturn(Optional.of(expectedTimeStamp));

        Optional<TimeStamp> actualTimeStamp = timeStampService.getTimeStampById(timeStampId);

        assertEquals(Optional.of(expectedTimeStamp), actualTimeStamp);
        verify(timeStampRepository, times(1)).findById(timeStampId);
    }

    @Test
    public void testCreateTimeStamp() {
        TimeStamp timeStampToCreate = new TimeStamp();
        TimeStamp createdTimeStamp = new TimeStamp();
        when(timeStampRepository.save(timeStampToCreate)).thenReturn(createdTimeStamp);

        TimeStamp actualTimeStamp = timeStampService.createTimeStamp(timeStampToCreate);

        assertEquals(createdTimeStamp, actualTimeStamp);
        verify(timeStampRepository, times(1)).save(timeStampToCreate);
    }

    @Test
    public void testUpdateTimeStamp() {
        long timeStampId = 1;
        TimeStamp updatedTimeStamp = new TimeStamp();
        when(timeStampRepository.findById(timeStampId)).thenReturn(Optional.of(new TimeStamp()));
        when(timeStampRepository.save(any())).thenReturn(updatedTimeStamp);

        TimeStamp actualTimeStamp = timeStampService.updateTimeStamp(timeStampId, updatedTimeStamp);

        assertEquals(updatedTimeStamp, actualTimeStamp);
        verify(timeStampRepository, times(1)).findById(timeStampId);
        verify(timeStampRepository, times(1)).save(any());
    }

    // Additional tests for other methods

}


 */