package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.ViewModel.TourListViewModel;
import at.technikum.tourplanner.event.EventAggregator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TourListViewModelTest {

    EventAggregator eventAggregator;
    TourRepository tourRepository;
    TourListViewModel tourListViewModel;

    @Test
    public void initialWTourListTest() {
        // Arrange
        eventAggregator = mock(EventAggregator.class);
        tourRepository = mock(TourRepository.class);

        List<String> tours = new ArrayList<>();
        tours.add("Tour 1");

        when(tourRepository.findAll()).thenReturn(tours);

        // Act
        tourListViewModel = new TourListViewModel(
                eventAggregator, tourRepository
        );

        // Assert
        assertEquals(1, tourListViewModel.getTourListView().size());
        assertEquals("Tour 1", tourListViewModel.getTourListView().get(0));
    }

}
