package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import javafx.beans.property.*;

import java.time.LocalDate;

public class AddTourLogViewModel {
    private final ObjectProperty<LocalDate> datePicker = new SimpleObjectProperty<>();
    private final StringProperty durationTextField = new SimpleStringProperty();
    private final StringProperty difficultyChoiceBox = new SimpleStringProperty("1");
    private final StringProperty rankingChoiceBox = new SimpleStringProperty("1");
    private final StringProperty commentTextArea = new SimpleStringProperty();
    private final TourLogService tourLogService;
    private final SelectedTourService selectedTourService;
    public AddTourLogViewModel(TourLogService tourLogService, SelectedTourService selectedTourService) {
        this.tourLogService=tourLogService;
        this.selectedTourService=selectedTourService;
    }

    public void addTourLog() {
        String tourName=selectedTourService.getTourName();
        String date = getDatePicker().toString();
        double duration = Double.parseDouble(durationTextField.get());
        int difficulty = Integer.parseInt(difficultyChoiceBox.get());
        int ranking = Integer.parseInt(rankingChoiceBox.get());
        String comment = getCommentTextArea();
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
