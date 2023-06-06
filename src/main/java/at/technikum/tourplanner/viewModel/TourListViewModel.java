package at.technikum.tourplanner.viewModel;

//import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.repository.TourRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {
    private final ObservableList<String> tourListView = FXCollections.observableArrayList();

    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final SelectedTourService selectedTourService;

    public TourListViewModel(EventAggregator eventAggregator, TourService tourService, SelectedTourService selectedTourService) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.selectedTourService=selectedTourService;

        tourListView.addAll(tourService.findAll());

        eventAggregator.addSubscriber(Event.NEW_TOUR, this::onNewTour);
    }

    private void onNewTour() {
        tourListView.clear();
        tourListView.addAll(tourService.findAll());
    }

    public void deleteTour(String tourName){
        Tour tour = tourService.findByName(tourName);
        if (tour != null) {
            tourService.delete(tourName);
            tourListView.remove(tourName);
        } else {
            System.out.println("Tour with name '" + tourName + "' does not exist.");
        }
//        tourService.delete(tourName);
//        tourListView.remove(tourName);
    }

    public void selectTourName(String tourName) {
        selectedTourService.setTourName(tourName);
        eventAggregator.publish(Event.TOUR_SELECTED);
        System.out.println(tourName);
    }

    public ObservableList<String> getTourListView() {
        return tourListView;
    }

    public void setTourListView(ObservableList<String> tours) {
        this.tourListView.setAll(tours);
    }

    public EventAggregator getEventAggregator() {
        return eventAggregator;
    }

    public TourRepository getTourRepository() {
        return getTourRepository();
    }

}
