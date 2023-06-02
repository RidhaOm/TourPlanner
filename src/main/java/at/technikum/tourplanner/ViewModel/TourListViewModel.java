package at.technikum.tourplanner.ViewModel;

import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {
    private final ObservableList<String> tourListView = FXCollections.observableArrayList();

    private final EventAggregator eventAggregator;
    private final TourRepository tourRepository;

    public TourListViewModel(EventAggregator eventAggregator, TourRepository tourRepository) {
        this.eventAggregator = eventAggregator;
        this.tourRepository = tourRepository;

        tourListView.addAll(tourRepository.findAll());

        eventAggregator.addSubscriber(Event.NEW_TOUR, this::onNewTour);
    }

    private void onNewTour() {
        tourListView.clear();
        tourListView.addAll(tourRepository.findAll());
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
        return tourRepository;
    }

}
