package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.dto.Route;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ModifyTourViewModel {

    private final StringProperty tourNameTextField = new SimpleStringProperty("");
    private final StringProperty fromTextField = new SimpleStringProperty("");
    private final StringProperty toTextField = new SimpleStringProperty("");
    private final StringProperty transportTypeChoiceBox = new SimpleStringProperty("");
    private final StringProperty tourDescriptionTextArea = new SimpleStringProperty("");

    private final SelectedTourService selectedTourService;
    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final RouteService routeService;


    public ModifyTourViewModel(EventAggregator eventAggregator, TourService tourService, RouteService routeService, SelectedTourService selectedTourService){
        this.selectedTourService = selectedTourService;
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.routeService = routeService;
        eventAggregator.addSubscriber(Event.TOUR_SELECTED, this::updateValues);
    }

    public void updateValues(){
        String selectedTourName = selectedTourService.getTourName();
        Tour tour = tourService.findByName(selectedTourName);
        setTourNameTextField(tour.getName());
        setFromTextField(tour.getTourFrom());
        setToTextField(tour.getTourTo());
        setTourDescriptionTextArea(tour.getDescription());
        setTransportTypeChoiceBox(tour.getTransportType());
    }

    public void editTour() {
        String selectedTourName = selectedTourService.getTourName();
        String name = tourNameTextField.get();
        String from = fromTextField.get();
        String to = toTextField.get();
        Double distance;
        String time;
        String description = tourDescriptionTextArea.get();
        String transportType = transportTypeChoiceBox.get();
        String routeInformation = "No information yet";
        Route route = routeService.getRoute(from, to);
        String imagePath = "src/main/resources/at/technikum/tourplanner/maps/" + name + ".jpg";
        routeService.saveMap(route.getSessionId(), imagePath);
        time = route.getFormattedTime();
        distance = route.getDistance();
        tourService.modify(selectedTourName, name, from, to, distance, time, description, transportType);
//        tourService.modify(selectedTourName, name, from, to, 12.0, "12:32", description, transportType, routeInformation);
        setTourNameTextField("");
        setFromTextField("");
        setToTextField("");
        setTourDescriptionTextArea("");
        setTransportTypeChoiceBox("");
        //System.out.println("Modify Tour: " + selectedTourName + "To " + name);
    }

    public String getTourNameTextField() {
        return tourNameTextField.get();
    }

    public StringProperty tourNameTextFieldProperty() {
        return tourNameTextField;
    }

    public void setTourNameTextField(String tourNameTextField) {
        this.tourNameTextField.set(tourNameTextField);
    }

    public String getFromTextField() {
        return fromTextField.get();
    }

    public StringProperty fromTextFieldProperty() {
        return fromTextField;
    }

    public void setFromTextField(String fromTextField) {
        this.fromTextField.set(fromTextField);
    }

    public String getToTextField() {
        return toTextField.get();
    }

    public StringProperty toTextFieldProperty() {
        return toTextField;
    }

    public void setToTextField(String toTextField) {
        this.toTextField.set(toTextField);
    }

    public String getTransportTypeChoiceBox() {
        return transportTypeChoiceBox.get();
    }

    public StringProperty transportTypeChoiceBoxProperty() {
        return transportTypeChoiceBox;
    }

    public void setTransportTypeChoiceBox(String transportTypeChoiceBox) {
        this.transportTypeChoiceBox.set(transportTypeChoiceBox);
    }

    public String getTourDescriptionTextArea() {
        return tourDescriptionTextArea.get();
    }

    public StringProperty tourDescriptionTextAreaProperty() {
        return tourDescriptionTextArea;
    }

    public void setTourDescriptionTextArea(String tourDescriptionTextArea) {
        this.tourDescriptionTextArea.set(tourDescriptionTextArea);
    }


}
