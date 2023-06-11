package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.viewModel.ModifyTourViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModifyTourViewModelTest {
    @Mock
    private EventAggregator eventAggregator;
    @Mock
    private TourService tourService;
    @Mock
    private RouteService routeService;
    @Mock
    private SelectedTourService selectedTourService;

    private ModifyTourViewModel modifyTourViewModel;

    @Before
    public void setup() {
        modifyTourViewModel = new ModifyTourViewModel(eventAggregator, tourService, routeService, selectedTourService);
    }
    @Test
    public void inputFieldsInitializedTest(){
        String name="tour 1";
        String tourFrom="Wien";
        String tourTo="Graz";
        Double distance=250.0;
        String time="2:30";
        String description="good";
        String transportType="Car";
        Tour tour = new Tour(name, tourFrom, tourTo, distance, time, description,transportType);

        when(selectedTourService.getTourName()).thenReturn(name);
        when(tourService.findByName(anyString())).thenReturn(tour);

        modifyTourViewModel.updateValues();
        assertEquals(name, modifyTourViewModel.getTourNameTextField());
        assertEquals(tourFrom, modifyTourViewModel.getFromTextField());
        assertEquals(tourTo, modifyTourViewModel.getToTextField());
        assertEquals(description, modifyTourViewModel.getTourDescriptionTextArea());
        assertEquals(transportType, modifyTourViewModel.getTransportTypeChoiceBox());
    }


    @Test
    public void editTour_MissingRequiredFields_InvalidInputs() {
        // Arrange
        modifyTourViewModel.setTourNameTextField("Tour 1");
        modifyTourViewModel.setFromTextField("");
        modifyTourViewModel.setToTextField("Salzburg");
        modifyTourViewModel.setTourDescriptionTextArea("A tour from Wien to Salzburg");

        // Act
        modifyTourViewModel.editTour();

        // Assert that the fields are not empty that means the method was stopped with return
        assertFalse(modifyTourViewModel.getTourNameTextField().isEmpty());
        assertFalse(modifyTourViewModel.getToTextField().isEmpty());
        assertFalse(modifyTourViewModel.getTourDescriptionTextArea().isEmpty());
    }

    @Test
    public void editTour_InvalidPlaceNames_InvalidInputs() {
        // Arrange
        modifyTourViewModel.setTourNameTextField("Tour 1");
        modifyTourViewModel.setFromTextField("Wien123");
        modifyTourViewModel.setToTextField("Salzburg456");
        modifyTourViewModel.setTourDescriptionTextArea("A tour from Wien to Salzburg");

        // Act
        modifyTourViewModel.editTour();

        // Assert that the fields are not empty that means the method was stopped with return
        assertFalse(modifyTourViewModel.getFromTextField().isEmpty());
        assertFalse(modifyTourViewModel.getToTextField().isEmpty());
        assertFalse(modifyTourViewModel.getTourDescriptionTextArea().isEmpty());
    }

}
