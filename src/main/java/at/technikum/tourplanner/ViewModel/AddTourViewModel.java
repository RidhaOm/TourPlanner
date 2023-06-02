package at.technikum.tourplanner.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;

public class AddTourViewModel {

    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");
    private final StringProperty testLabel = new SimpleStringProperty("");

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

    public void saveTour(){

        System.out.println("Tour Name: " + tourNameTextField.get());
        System.out.println("From: " + fromTextField.get());
        System.out.println("To: " + toTextField.get());

        testLabel.set(tourNameTextField.get());

    }

}
