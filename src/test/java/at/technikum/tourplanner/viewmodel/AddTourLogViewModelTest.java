package at.technikum.tourplanner.viewmodel;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import at.technikum.tourplanner.viewModel.AddTourLogViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddTourLogViewModelTest {
    private TourLogService tourLogService;
    private SelectedTourService selectedTourService;
    private AddTourLogViewModel addTourLogViewModel;

    @BeforeEach
    void setUp() {
        tourLogService = mock(TourLogService.class);
        selectedTourService = mock(SelectedTourService.class);
        addTourLogViewModel = new AddTourLogViewModel(tourLogService, selectedTourService);
    }

    @Test
    public void inputFieldsInitializedTest(){
        assertEquals(LocalDate.now(), addTourLogViewModel.getDatePicker());
        assertEquals("", addTourLogViewModel.getDurationTextField());
        assertEquals("1", addTourLogViewModel.getRankingChoiceBox());
        assertEquals("1", addTourLogViewModel.getDifficultyChoiceBox());
        assertEquals("", addTourLogViewModel.getCommentTextArea());
    }

    @Test
    void addTourLog_ValidInput_Success() {
        // Arrange
        addTourLogViewModel.setDatePicker(LocalDate.now());
        addTourLogViewModel.setDurationTextField("2");
        addTourLogViewModel.setRankingChoiceBox("1");
        addTourLogViewModel.setDifficultyChoiceBox("2");
        addTourLogViewModel.setCommentTextArea("This is a comment");


        // Act
        addTourLogViewModel.addTourLog();

        // Assert
        assertEquals("", addTourLogViewModel.getDurationTextField());
        assertEquals("", addTourLogViewModel.getCommentTextArea());
        assertEquals("1", addTourLogViewModel.getDifficultyChoiceBox());
        assertEquals("1", addTourLogViewModel.getRankingChoiceBox());
        assertNotNull(addTourLogViewModel.getDatePicker());
    }

    @Test
    void addTourLog_MissingDate_Error() {
        // Arrange
        String tourName = "Tour 1";

        when(selectedTourService.getTourName()).thenReturn(tourName);

        // Act
        addTourLogViewModel.addTourLog();

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
        addTourLogViewModel.datePickerProperty().bindBidirectional(datePickerProperty);

        StringProperty durationTextFieldProperty = new SimpleStringProperty(duration);
        addTourLogViewModel.durationTextFieldProperty().bindBidirectional(durationTextFieldProperty);

        // Act
        addTourLogViewModel.addTourLog();

        // Assert
        verify(selectedTourService).getTourName();
        verifyNoInteractions(tourLogService);
    }

    @Test
    void addTourLog_MissingComment_Success() {
        // Arrange
        addTourLogViewModel.setDatePicker(LocalDate.now());
        addTourLogViewModel.setDurationTextField("2");
        addTourLogViewModel.setRankingChoiceBox("1");
        addTourLogViewModel.setDifficultyChoiceBox("2");

        // Act
        addTourLogViewModel.addTourLog();

        // Assert
        assertEquals("", addTourLogViewModel.getDurationTextField());
        assertEquals("", addTourLogViewModel.getCommentTextArea());
        assertEquals("1", addTourLogViewModel.getDifficultyChoiceBox());
        assertEquals("1", addTourLogViewModel.getRankingChoiceBox());
        assertNotNull(addTourLogViewModel.getDatePicker());
    }
}
