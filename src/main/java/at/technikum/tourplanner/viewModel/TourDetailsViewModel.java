package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.view.TourDetailsView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;


public class TourDetailsViewModel {

    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final SelectedTourService selectedTourService;

    private final StringProperty tourDetailsLabel = new SimpleStringProperty("");
    private final ObjectProperty<Image> mapview = new SimpleObjectProperty();
    private static final Logger logger = Logger.getLogger(TourDetailsViewModel.class);


    public String getTourDetailsLabel() {
        return tourDetailsLabel.get();
    }

    public StringProperty tourDetailsLabelProperty() {
        return tourDetailsLabel;
    }

    public void setTourDetailsLabel(String tourDetailsLabel) {
        this.tourDetailsLabel.set(tourDetailsLabel);
    }
    public ObjectProperty mapviewProperty() {return mapview;}
    public TourDetailsViewModel(EventAggregator eventAggregator, TourService tourService, SelectedTourService selectedTourService) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.selectedTourService = selectedTourService;
        eventAggregator.addSubscriber(Event.TOUR_SELECTED, this::updateTourDetailsLabel);
        eventAggregator.addSubscriber(Event.TOUR_SELECTED, this::updateMapView);
        eventAggregator.addSubscriber(Event.NEW_TOUR_LOG, this::updateTourDetailsLabel);
        eventAggregator.addSubscriber(Event.TOUR_LOG_DELETED, this::updateTourDetailsLabel);
        eventAggregator.addSubscriber(Event.TOUR_MODIFIED, this::updateTourDetailsLabel);
    }

    public void writeNotify(){
        String tourName = selectedTourService.getTourName();
        logger.info("Selected tour: " + tourName + " (From ViewModel)");
        System.out.println("item "+tourName+" selected From ViewModel");
    }

    public void updateMapView() {
        String selectedTourName = getSelectedTourName();
        if (selectedTourName != null) {
            String imagePath = "/at/technikum/tourplanner/maps/" + selectedTourName + ".jpg";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            mapview.set(image);
            logger.info("MapView updated for tour: " + selectedTourName);
        }
    }

    public void updateTourDetailsLabel() {
        String selectedTourName = getSelectedTourName();
        String tourDetails = tourService.getTourDetailsByName(selectedTourName);
        if (tourDetails != null) {
            tourDetailsLabel.set(tourDetails);
            logger.info("Tour details label updated for tour: " + selectedTourName);
        }
    }

    public String getSelectedTourName(){
        return selectedTourService.getTourName();
    }
}
