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


    private final TourRepository tourRepository;

    public AddTourViewModel(TourRepository tourRepository) {

        this.tourRepository=tourRepository;
    }


    public StringProperty tourNameTextFieldProperty() {
        return tourNameTextField;
    }
    public StringProperty fromTextFieldProperty() {
        return fromTextField;
    }
    public StringProperty toTextFieldProperty() {
        return toTextField;
    }


    public void saveTour(){

        System.out.println("Tour Name: " + tourNameTextField.get());
        System.out.println("From: " + fromTextField.get());
        System.out.println("To: " + toTextField.get());

        tourRepository.save(tourNameTextField.get());

        tourNameTextField.set("");
        fromTextField.set("");
        toTextField.set("");
    }
}
