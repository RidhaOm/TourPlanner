package at.technikum.tourplanner.viewmodel;

import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.service.SelectedTourLogService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import at.technikum.tourplanner.view.MainView;
import at.technikum.tourplanner.viewModel.AddTourLogViewModel;
import at.technikum.tourplanner.viewModel.ModifyTourLogViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ModifyTourLogViewModelTest {
    private EventAggregator eventAggregator;
    private TourLogService tourLogService;
    private SelectedTourLogService selectedTourLogService;
    private SelectedTourService selectedTourService;
    private ModifyTourLogViewModel modifyTourLogViewModel;

    @BeforeEach
    void setUp() {
        eventAggregator = mock(EventAggregator.class);
        tourLogService = mock(TourLogService.class);
        selectedTourLogService = mock(SelectedTourLogService.class);
        selectedTourService = mock(SelectedTourService.class);
        modifyTourLogViewModel = new ModifyTourLogViewModel(eventAggregator, tourLogService, selectedTourLogService, selectedTourService);
    }

    @Test
    void modifyTourLog_ValidInput_Success() {
        // Arrange
        modifyTourLogViewModel.setDatePicker(LocalDate.now());
        modifyTourLogViewModel.setDurationTextField("2");
        modifyTourLogViewModel.setRankingChoiceBox("1");
        modifyTourLogViewModel.setDifficultyChoiceBox("2");
        modifyTourLogViewModel.setCommentTextArea("This is a comment");


        // Act
        modifyTourLogViewModel.modifyTourLog();

        // Assert
        assertEquals("", modifyTourLogViewModel.getDurationTextField());
        assertEquals("", modifyTourLogViewModel.getCommentTextArea());
        assertEquals("1", modifyTourLogViewModel.getDifficultyChoiceBox());
        assertEquals("1", modifyTourLogViewModel.getRankingChoiceBox());
        //assertNotNull(modifyTourLogViewModel.getDatePicker());
    }

    @Test
    void modifyTourLog_MissingDate_Error() {
        // Arrange
        String tourName = "Tour 1";

        when(selectedTourService.getTourName()).thenReturn(tourName);

        // Act
        modifyTourLogViewModel.modifyTourLog();

        // Assert
        verify(selectedTourService).getTourName();
        verifyNoInteractions(tourLogService);
    }

    @Test
    void addTourLog_InvalidDuration_Error() {
        // Arrange
        String tourName = "Tour 1";
        LocalDate date = LocalDate.now();
        String duration = "abc";

        when(selectedTourService.getTourName()).thenReturn(tourName);

        ObjectProperty<LocalDate> datePickerProperty = new SimpleObjectProperty<>(date);
        modifyTourLogViewModel.datePickerProperty().bindBidirectional(datePickerProperty);

        StringProperty durationTextFieldProperty = new SimpleStringProperty(duration);
        modifyTourLogViewModel.durationTextFieldProperty().bindBidirectional(durationTextFieldProperty);

        // Act
        modifyTourLogViewModel.modifyTourLog();

        // Assert
        verify(selectedTourService).getTourName();
        verifyNoInteractions(tourLogService);
    }
}
