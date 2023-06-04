package at.technikum.tourplanner.ViewModel;

//import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.Service.TourService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddTourViewModel {
    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");

    private final TourService tourService;

    public AddTourViewModel(TourService tourService) {
        this.tourService = tourService;
    }

    public String getTourNameTextField() {
        return tourNameTextField.get();
    }

    public void setTourNameTextField(String tourName) {
        tourNameTextField.set(tourName);
    }

    public StringProperty tourNameTextFieldProperty() {
        return tourNameTextField;
    }

    public String getFromTextField() {
        return fromTextField.get();
    }

    public void setFromTextField(String from) {
        fromTextField.set(from);
    }

    public StringProperty fromTextFieldProperty() {
        return fromTextField;
    }

    public String getToTextField() {
        return toTextField.get();
    }

    public void setToTextField(String to) {
        toTextField.set(to);
    }

    public StringProperty toTextFieldProperty() {
        return toTextField;
    }

    public void saveTour() {
        System.out.println("Tour Name: " + getTourNameTextField());
        System.out.println("From: " + getFromTextField());
        System.out.println("To: " + getToTextField());

        tourService.save(getTourNameTextField());

        setTourNameTextField("");
        setFromTextField("");
        setToTextField("");
    }
}
