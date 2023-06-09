package at.technikum.tourplanner.viewModel;


import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.service.SelectedTourLogService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class ModifyTourLogViewModel {

    private final ObjectProperty<LocalDate> datePicker = new SimpleObjectProperty<>();
    private final StringProperty durationTextField = new SimpleStringProperty();
    private final StringProperty difficultyChoiceBox = new SimpleStringProperty("1");
    private final StringProperty rankingChoiceBox = new SimpleStringProperty("1");
    private final StringProperty commentTextArea = new SimpleStringProperty();
    private final EventAggregator eventAggregator;
    private final TourLogService tourLogService;
    private final SelectedTourLogService selectedTourLogService;
    private final SelectedTourService selectedTourService;

    public ModifyTourLogViewModel(EventAggregator eventAggregator, TourLogService tourLogService, SelectedTourLogService selectedTourLogService, SelectedTourService selectedTourService){
        this.tourLogService=tourLogService;
        this.eventAggregator = eventAggregator;
        this.selectedTourLogService = selectedTourLogService;
        this.selectedTourService = selectedTourService;
        eventAggregator.addSubscriber(Event.TOUR_LOG_SELECTED, this::updateValues);
    }

    public void updateValues(){
        String selectedTourLogName = selectedTourLogService.getTourLogName();
        TourLog tourLog = tourLogService.findByName(selectedTourLogName);
        setDurationTextField(String.valueOf(tourLog.getDuration()));
        setCommentTextArea(tourLog.getComment());
        setDifficultyChoiceBox(String.valueOf(tourLog.getDifficulty()));
        setRankingChoiceBox(String.valueOf(tourLog.getRanking()));
        setDatePicker(LocalDate.now());
    }

    public void modifyTourLog() {
        String selectedTourLogName = selectedTourLogService.getTourLogName();
        String tourName = selectedTourService.getTourName();
        String date = getDatePicker().toString();
        double duration = Double.parseDouble(getDurationTextField());
        int difficulty = Integer.parseInt(getDifficultyChoiceBox());
        int ranking = Integer.parseInt(getRankingChoiceBox());
        String comment = getCommentTextArea();
        tourLogService.modify(selectedTourLogName, tourName, date, duration, difficulty, ranking, comment);
    }

    public LocalDate getDatePicker() {
        return datePicker.get();
    }

    public ObjectProperty<LocalDate> datePickerProperty() {
        return datePicker;
    }

    public void setDatePicker(LocalDate datePicker) {
        this.datePicker.set(datePicker);
    }

    public String getDurationTextField() {
        return durationTextField.get();
    }

    public StringProperty durationTextFieldProperty() {
        return durationTextField;
    }

    public void setDurationTextField(String durationTextField) {
        this.durationTextField.set(durationTextField);
    }

    public String getDifficultyChoiceBox() {
        return difficultyChoiceBox.get();
    }

    public StringProperty difficultyChoiceBoxProperty() {
        return difficultyChoiceBox;
    }

    public void setDifficultyChoiceBox(String difficultyChoiceBox) {
        this.difficultyChoiceBox.set(difficultyChoiceBox);
    }

    public String getRankingChoiceBox() {
        return rankingChoiceBox.get();
    }

    public StringProperty rankingChoiceBoxProperty() {
        return rankingChoiceBox;
    }

    public void setRankingChoiceBox(String rankingChoiceBox) {
        this.rankingChoiceBox.set(rankingChoiceBox);
    }

    public String getCommentTextArea() {
        return commentTextArea.get();
    }

    public StringProperty commentTextAreaProperty() {
        return commentTextArea;
    }

    public void setCommentTextArea(String commentTextArea) {
        this.commentTextArea.set(commentTextArea);
    }

    public TourLogService getTourLogService() {
        return tourLogService;
    }

    public SelectedTourLogService getSelectedTourLogService() {
        return selectedTourLogService;
    }

}
