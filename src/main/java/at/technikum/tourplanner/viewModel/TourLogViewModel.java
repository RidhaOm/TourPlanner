package at.technikum.tourplanner.viewModel;


import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import at.technikum.tourplanner.service.TourService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourLogViewModel {


    private final ObservableList<String> tourLogListView = FXCollections.observableArrayList();
    private final EventAggregator eventAggregator;
    private final TourLogService tourLogService;
    private final SelectedTourService selectedTourService;
    public TourLogViewModel(EventAggregator eventAggregator, TourLogService tourLogService, SelectedTourService selectedTourService){
        this.eventAggregator = eventAggregator;
        this.tourLogService = tourLogService;
        this.selectedTourService=selectedTourService;
        tourLogListView.addAll(tourLogService.findAll());
        eventAggregator.addSubscriber(Event.NEW_TOUR_LOG, this::onNewTourLog);
    }

    private void onNewTourLog() {
        tourLogListView.clear();
        tourLogListView.addAll(tourLogService.findAll());
    }

    public ObservableList<String> getTourLogListView() {
        return tourLogListView;
    }

}
