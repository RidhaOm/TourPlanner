package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.viewModel.AddTourViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddTourViewModelTest {

    @Mock
    private RouteService routeService;

    @Mock
    private TourService tourService;

    private AddTourViewModel addTourViewModel;

    @Before
    public void setup() {
        addTourViewModel = new AddTourViewModel(routeService, tourService);
    }

    @Test
    public void inputFieldsInitializedTest(){
        assertEquals("", addTourViewModel.getTourNameTextField());
        assertEquals("", addTourViewModel.getFromTextField());
        assertEquals("", addTourViewModel.getToTextField());
        assertEquals("", addTourViewModel.getTourDescriptionTextArea());
        assertEquals("Car", addTourViewModel.getTransportTypeChoiceBox());
    }

    @Test
    public void inputStringClearTest() {
        // Arrange
        addTourViewModel.setTourNameTextField("Tour 1");
        addTourViewModel.setFromTextField("Wien");
        addTourViewModel.setToTextField("Salzburg");

        // Mock the behavior of RouteService
        Route route = new Route();
        route.setSessionId("session123");
        when(routeService.getRoute("Wien", "Salzburg", "Car")).thenReturn(route);

        // Act
        addTourViewModel.saveTour();

        // Assert
        assertEquals("", addTourViewModel.getTourNameTextField());
        assertEquals("", addTourViewModel.getFromTextField());
        assertEquals("", addTourViewModel.getToTextField());
    }

}