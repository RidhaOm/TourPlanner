package at.technikum.tourplanner.viewModel;

//import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.model.Tour;
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

    public TourListViewModel(EventAggregator eventAggregator, TourService tourService) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;

        tourListView.addAll(tourService.findAll());

        eventAggregator.addSubscriber(Event.NEW_TOUR, this::onNewTour);
    }

    private void onNewTour() {
        tourListView.clear();
        tourListView.addAll(tourService.findAll());
    }

    public void deleteTour(Long id){
//        tourService.delete(id);
//        tourListView.removeIf(tourName -> tourService.findById(id).getName().equals(tourName));
        String tourName = tourService.findById(id).getName();
        tourService.delete(id);
        tourListView.remove(tourName);
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
