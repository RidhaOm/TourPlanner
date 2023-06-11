package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.repository.TourRepository;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.TourService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.log4j.Logger;


public class AddTourViewModel {
    private static final Logger logger = Logger.getLogger(TourRepository.class);
    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");
    private final StringProperty transportTypeChoiceBox = new SimpleStringProperty("Car");
    private final StringProperty tourDescriptionTextArea = new SimpleStringProperty("");
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

    public String getTransportTypeChoiceBox() {
        return transportTypeChoiceBox.get();
    }

    public void setTransportTypeChoiceBox(String transportType) {
        transportTypeChoiceBox.set(transportType);
    }

    public StringProperty transportTypeChoiceBoxProperty() {
        return transportTypeChoiceBox;
    }

    public String getTourDescriptionTextArea() {
        return tourDescriptionTextArea.get();
    }

    public void setTourDescriptionTextArea(String description) {
        tourDescriptionTextArea.set(description);
    }

    public StringProperty tourDescriptionTextAreaProperty() {
        return tourDescriptionTextArea;
    }

    public void saveTour() {
        String name = tourNameTextField.get();
        String from = fromTextField.get();
        String to = toTextField.get();
        String description = tourDescriptionTextArea.get();
        String transportType = transportTypeChoiceBox.get();

        // Validate inputs
        if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
            logger.error("Please fill in all the required fields.");
            return;
        }

        if (!from.matches("[a-zA-Z]+") || !to.matches("[a-zA-Z]+")) {
            logger.error("From and To fields should be place names and contain only alphabetic characters.");
            return;
        }

        if (description.isEmpty()) {
            description = "No description yet";
        }

        // Validate route
//        if (!validateRoute(from, to, transportType)) {
//            logger.error("Invalid route. Please check the From and To fields.");
//            return;
//        }

        // Route is valid, continue saving the tour
        Route route = routeService.getRoute(from, to, transportType);
        String imagePath = "src/main/resources/at/technikum/tourplanner/maps/" + name + ".jpg";
        routeService.saveMap(route.getSessionId(), imagePath);
        String time = route.getFormattedTime();
        Double distance = route.getDistance();
        tourService.save(name, from, to, distance, time, description, transportType);

        // Clear input fields
        tourNameTextField.set("");
        fromTextField.set("");
        toTextField.set("");
        tourDescriptionTextArea.set("");
    }

    private boolean validateRoute(String from, String to, String transportType) {
        try {
            // Attempt to get the route without saving it
            Route route = routeService.getRoute(from, to, transportType);
            return route != null;
        } catch (Exception e) {
            logger.error("Error validating the route: " + e.getMessage());
            return false;
        }
    }


}

