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

public class TourDetailsViewModel {

    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final SelectedTourService selectedTourService;

    private final ObjectProperty<Image> mapview = new SimpleObjectProperty();

    public ObjectProperty mapviewProperty() {return mapview;}
    public TourDetailsViewModel(EventAggregator eventAggregator, TourService tourService, SelectedTourService selectedTourService) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.selectedTourService = selectedTourService;
        eventAggregator.addSubscriber(Event.TOUR_SELECTED, this::updateMapView);
    }

    public void writeNotify(){
        String tourName = selectedTourService.getTourName();
        System.out.println("item "+tourName+" selected From ViewModel");
    }

    public void updateMapView() {
        String selectedTourName = getSelectedTourName();
        if (selectedTourName != null) {
            String imagePath = "/at/technikum/tourplanner/maps/" + selectedTourName + ".jpg";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            mapview.set(image);
        }
    }
    public String getSelectedTourName(){
        return selectedTourService.getTourName();
    }
}
