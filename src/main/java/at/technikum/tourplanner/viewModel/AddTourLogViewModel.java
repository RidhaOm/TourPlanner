package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.repository.TourRepository;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import javafx.beans.property.*;
import org.apache.log4j.Logger;

import java.time.LocalDate;

public class AddTourLogViewModel {
    private final ObjectProperty<LocalDate> datePicker = new SimpleObjectProperty<>(LocalDate.now());
    private final StringProperty durationTextField = new SimpleStringProperty("");
    private final StringProperty difficultyChoiceBox = new SimpleStringProperty("1");
    private final StringProperty rankingChoiceBox = new SimpleStringProperty("1");
    private final StringProperty commentTextArea = new SimpleStringProperty("");
    private final TourLogService tourLogService;
    private static final org.apache.log4j.Logger logger = Logger.getLogger(AddTourLogViewModel.class);    private final SelectedTourService selectedTourService;
    public AddTourLogViewModel(TourLogService tourLogService, SelectedTourService selectedTourService) {
        this.tourLogService=tourLogService;
        this.selectedTourService=selectedTourService;
    }

    public void addTourLog() {
        String tourName = selectedTourService.getTourName();

        // Validate DatePicker input
        LocalDate selectedDate = getDatePicker();
        if (selectedDate == null) {
            logger.error("Date is null");
            return; // Abort adding tour log
        }

        String date = selectedDate.toString();

        // Validate durationTextField input
        if (getDurationTextField()==null) {
            logger.error("Duration field is null");
            return; // Abort adding tour log
        }
        double duration;
        try {
            duration = Double.parseDouble(getDurationTextField());
        } catch (NumberFormatException e) {
            logger.error("Invalid duration input: " + getDurationTextField());
            return; // Abort adding tour log
        }

        int difficulty = Integer.parseInt(getDifficultyChoiceBox());
        int ranking = Integer.parseInt(getRankingChoiceBox());
        String comment = getCommentTextArea();
        if (comment == "") {comment = "No comment";}

        tourLogService.saveTourLog(tourName, date, duration, difficulty, ranking, comment);

        setDurationTextField("");
        setCommentTextArea("");
        setDifficultyChoiceBox("1");
        setRankingChoiceBox("1");
        setDatePicker(LocalDate.now());
    }



    public ObjectProperty<LocalDate> datePickerProperty() {
        return datePicker;
    }

    public LocalDate getDatePicker() {
        return datePicker.get();
    }

    public void setDatePicker(LocalDate datePicker) {
        this.datePicker.set(datePicker);
    }

    public StringProperty durationTextFieldProperty() {
        return durationTextField;
    }

    public String getDurationTextField() {
        return durationTextField.get();
    }

    public void setDurationTextField(String durationTextField) {
        this.durationTextField.set(durationTextField);
    }

    public StringProperty difficultyChoiceBoxProperty() {
        return difficultyChoiceBox;
    }

    public String getDifficultyChoiceBox() {
        return difficultyChoiceBox.get();
    }

    public void setDifficultyChoiceBox(String difficultyChoiceBox) {
        this.difficultyChoiceBox.set(difficultyChoiceBox);
    }

    public StringProperty rankingChoiceBoxProperty() {
        return rankingChoiceBox;
    }

    public String getRankingChoiceBox() {
        return rankingChoiceBox.get();
    }

    public void setRankingChoiceBox(String rankingChoiceBox) {
        this.rankingChoiceBox.set(rankingChoiceBox);
    }

    public StringProperty commentTextAreaProperty() {
        return commentTextArea;
    }

    public String getCommentTextArea() {
        return commentTextArea.get();
    }

    public void setCommentTextArea(String commentTextArea) {
        this.commentTextArea.set(commentTextArea);
    }
}
