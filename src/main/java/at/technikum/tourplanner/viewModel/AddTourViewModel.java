package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.TourService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class AddTourViewModel {
    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");
    private final StringProperty informationTextfield = new SimpleStringProperty("");
    private final RouteService routeService;
    private final TourService tourService;

    public AddTourViewModel(RouteService routeService, TourService tourService) {
        this.routeService = routeService;
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
    public String getInformationTextField() {
        return informationTextfield.get();
    }

    public void setInformationTextField(String from) {
        informationTextfield.set(from);
    }
    public StringProperty informationTextFieldProperty() {
        return informationTextfield;
    }

    public void saveTour() {
        String name = tourNameTextField.get();
        String from = fromTextField.get();
        String to = toTextField.get();
        Double distance;
        String time;
        String description = "No description yet";
        String transportType = "Walking";
        String routeInformation = "No informations yet";

        Route route = routeService.getRoute(from, to);
        routeService.saveMap(route.getSessionId(), "map.jpg");
        time = route.getFormattedTime();
        distance = route.getDistance();
        String information = "From "
                + from + " to "
                + to + " in "
                + time
                + " (" + distance + ")";


        setInformationTextField(information);
        System.out.println(information);
        tourService.save(name, from, to, distance, time, description, transportType, routeInformation);
        setTourNameTextField("");
        setFromTextField("");
        setToTextField("");
    }
}
