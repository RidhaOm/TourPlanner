package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.Service.TourService;
import at.technikum.tourplanner.ViewModel.AddTourViewModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AddTourViewModelTest {

    TourService tourService;

    AddTourViewModel addTourViewModel;

    @Test
    public void inputStringClearTest() {
        // Arrange
        tourService = mock(TourService.class);
        addTourViewModel = new AddTourViewModel(tourService);
        addTourViewModel.setTourNameTextField("Tour 1");
        addTourViewModel.setFromTextField("Wien");
        addTourViewModel.setToTextField("Salzburg");

        // Act
        addTourViewModel.saveTour();

        // Assert
        assertEquals("", addTourViewModel.getTourNameTextField());
        assertEquals("", addTourViewModel.getFromTextField());
        assertEquals("", addTourViewModel.getToTextField());

    }

}
