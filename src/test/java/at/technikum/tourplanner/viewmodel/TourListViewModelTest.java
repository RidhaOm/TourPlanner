package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.SearchService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.viewModel.TourListViewModel;
import at.technikum.tourplanner.event.EventAggregator;
import javafx.collections.ObservableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    public void deleteTourTest() {
        // Arrange
        eventAggregator = mock(EventAggregator.class);
        tourService = mock(TourService.class);

        List<String> tours = new ArrayList<>();
        String tourName = "Tour 1";
        Tour tour = new Tour(tourName, "wien", "graz");
        tours.add(tourName);
        when(tourService.findByName(tourName)).thenReturn(tour);

        // Act
        tourListViewModel = new TourListViewModel(
                eventAggregator, tourService, selectedTourService, searchService
        );
        tourListViewModel.deleteTour(tourName);

        // Assert That the list is empty
        assertEquals(0, tourListViewModel.getTourListView().size());
    }

    @Test
    public void deleteTourNonExistingTest(){
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


        // Assert That the size of the list is the same
        assertEquals(1, tourListViewModel.getTourListView().size());
    }

    @Test
    public void selectTourNameTest() {
        // Arrange
        eventAggregator = mock(EventAggregator.class);
        tourService = mock(TourService.class);
        selectedTourService = mock(SelectedTourService.class);

        List<String> tours = new ArrayList<>();
        String tourName = "tour1";
        tours.add(tourName);

        // Act
        tourListViewModel = new TourListViewModel(
                eventAggregator, tourService, selectedTourService, searchService
        );

        tourListViewModel.selectTourName(tourName);

        // Assert
        assertEquals(tourName, tourListViewModel.getSelectedTourName());
        verify(selectedTourService).setTourName(tourName);
        verify(eventAggregator).publish(Event.TOUR_SELECTED);
    }
    @Test
    public void onNewTourEventTest() {
        // Arrange
        eventAggregator = mock(EventAggregator.class);
        tourService = mock(TourService.class);
        List<String> tours = Arrays.asList("Tour 1", "Tour 2");
        when(tourService.findAll()).thenReturn(tours);

        // Act
        tourListViewModel = new TourListViewModel(
                eventAggregator, tourService, selectedTourService, searchService
        );
        tourListViewModel.getEventAggregator()
                .publish(Event.NEW_TOUR);

        // Assert
        ObservableList<String> tourListView = tourListViewModel.getTourListView();
        assertEquals(2, tourListView.size());
        assertEquals("Tour 1", tourListView.get(0));
        assertEquals("Tour 2", tourListView.get(1));
    }

}
