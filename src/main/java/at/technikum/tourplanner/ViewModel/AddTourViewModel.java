package at.technikum.tourplanner.ViewModel;

import at.technikum.tourplanner.Model.TourRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class AddTourViewModel {

    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");
    private final StringProperty testLabel = new SimpleStringProperty("");

    private final ObservableList<String> testList = FXCollections.observableArrayList();

    private final TourRepository tourRepository = new TourRepository();


    public StringProperty tourNameTextFieldProperty() {
        return tourNameTextField;
    }
    public StringProperty fromTextFieldProperty() {
        return fromTextField;
    }
    public StringProperty toTextFieldProperty() {
        return toTextField;
    }
    public StringProperty testLabelProperty() {
        return testLabel;
    }

    public ObservableList<String> getTestList() {
        return testList;
    }


    public AddTourViewModel() {
        testList.addAll(tourRepository.findAll());

        tourRepository.addNewWordListener(this::addNewTour);
    }

    private void addNewTour(String tourName) {
        testList.add(tourName);
    }

    public void saveTour(){

        System.out.println("Tour Name: " + tourNameTextField.get());
        System.out.println("From: " + fromTextField.get());
        System.out.println("To: " + toTextField.get());

        testLabel.set(tourNameTextField.get());
        tourRepository.save(tourNameTextField.get());

        //testList.add(tourNameTextField.get());
    }
}
