package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.service.SearchService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.viewModel.TourListViewModel;
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
    TourService tourService;
    TourListViewModel tourListViewModel;
    SelectedTourService selectedTourService;
    SearchService searchService;

    @Test
    public void initialWTourListTest() {
        // Arrange
        eventAggregator = mock(EventAggregator.class);
        tourService = mock(TourService.class);

        List<String> tours = new ArrayList<>();
        tours.add("Tour 1");

        when(tourService.findAll()).thenReturn(tours);

        // Act
        tourListViewModel = new TourListViewModel(
                eventAggregator, tourService, selectedTourService, searchService
        );

        // Assert
        assertEquals(1, tourListViewModel.getTourListView().size());
        assertEquals("Tour 1", tourListViewModel.getTourListView().get(0));
    }

}
