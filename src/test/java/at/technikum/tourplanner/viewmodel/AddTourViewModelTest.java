package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.repository.TourRepository;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.viewModel.AddTourViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static org.junit.Assert.assertFalse;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
    public void saveTour_ValidInputs_Success() {
        // Arrange
        addTourViewModel.setTourNameTextField("Tour 1");
        addTourViewModel.setFromTextField("Wien");
        addTourViewModel.setToTextField("Salzburg");
        addTourViewModel.setTourDescriptionTextArea("A tour from Wien to Salzburg");

        // Mock the behavior of RouteService
        Route route = new Route();
        route.setSessionId("session123");
        when(routeService.getRoute("Wien", "Salzburg", "Car")).thenReturn(route);

        // Act
        addTourViewModel.saveTour();

        // Assert
        verify(routeService).getRoute("Wien", "Salzburg", "Car");
        verify(tourService).save("Tour 1", "Wien", "Salzburg", 0.0, null, "A tour from Wien to Salzburg", "Car");
        assertEquals("", addTourViewModel.getTourNameTextField());
        assertEquals("", addTourViewModel.getFromTextField());
        assertEquals("", addTourViewModel.getToTextField());
        assertEquals("", addTourViewModel.getTourDescriptionTextArea());
    }

    @Test
    public void saveTour_MissingRequiredFields_InvalidInputs() {
        // Arrange
        addTourViewModel.setTourNameTextField("Tour 1");
        addTourViewModel.setFromTextField("");
        addTourViewModel.setToTextField("Salzburg");
        addTourViewModel.setTourDescriptionTextArea("A tour from Wien to Salzburg");

        // Act
        addTourViewModel.saveTour();

        // Assert that the fields are not empty that means the method was stopped with return
        assertFalse(addTourViewModel.getTourNameTextField().isEmpty());
        assertFalse(addTourViewModel.getToTextField().isEmpty());
        assertFalse(addTourViewModel.getTourDescriptionTextArea().isEmpty());
    }

    @Test
    public void saveTour_InvalidPlaceNames_InvalidInputs() {
        // Arrange
        addTourViewModel.setTourNameTextField("Tour 1");
        addTourViewModel.setFromTextField("Wien123");
        addTourViewModel.setToTextField("Salzburg456");
        addTourViewModel.setTourDescriptionTextArea("A tour from Wien to Salzburg");

        // Act
        addTourViewModel.saveTour();

        // Assert that the fields are not empty that means the method was stopped with return
        assertFalse(addTourViewModel.getFromTextField().isEmpty());
        assertFalse(addTourViewModel.getToTextField().isEmpty());
        assertFalse(addTourViewModel.getTourDescriptionTextArea().isEmpty());
    }

    @Test
    public void saveTour_DefaultDescription_Success() {
        // Arrange
        addTourViewModel.setTourNameTextField("Tour 1");
        addTourViewModel.setFromTextField("Wien");
        addTourViewModel.setToTextField("Salzburg");
        addTourViewModel.setTourDescriptionTextArea("");

        // Mock the behavior of RouteService
        Route route = new Route();
        route.setSessionId("session123");
        when(routeService.getRoute("Wien", "Salzburg", "Car")).thenReturn(route);

        // Act
        addTourViewModel.saveTour();

        // Assert
        verify(routeService).getRoute("Wien", "Salzburg", "Car");
        verify(tourService).save("Tour 1", "Wien", "Salzburg", 0.0, null, "No description yet", "Car");
        assertEquals("", addTourViewModel.getTourNameTextField());
        assertEquals("", addTourViewModel.getFromTextField());
        assertEquals("", addTourViewModel.getToTextField());
        assertEquals("", addTourViewModel.getTourDescriptionTextArea());
    }

}
