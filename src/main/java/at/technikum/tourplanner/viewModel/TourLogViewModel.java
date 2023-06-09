package at.technikum.tourplanner.viewModel;


import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.service.SearchService;
import at.technikum.tourplanner.service.SelectedTourLogService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourLogService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourLogViewModel {


    private final ObservableList<String> tourLogListView = FXCollections.observableArrayList();
    private final EventAggregator eventAggregator;
    private final TourLogService tourLogService;
    private final SelectedTourService selectedTourService;
    private final SelectedTourLogService selectedTourLogService;
    private final SearchService searchService;
    private final StringProperty selectedTourLog = new SimpleStringProperty();
    private final StringProperty selectedTour = new SimpleStringProperty();
    public TourLogViewModel(EventAggregator eventAggregator, TourLogService tourLogService, SelectedTourService selectedTourService, SelectedTourLogService selectedTourLogService, SearchService searchService){
        this.eventAggregator = eventAggregator;
        this.tourLogService = tourLogService;
        this.selectedTourService=selectedTourService;
        this.selectedTourLogService = selectedTourLogService;
        this.searchService = searchService;
        tourLogListView.addAll(tourLogService.findByTourName(getSelectedTourName()));
        eventAggregator.addSubscriber(Event.TOUR_SELECTED, this::enableButton);
        eventAggregator.addSubscriber(Event.TOUR_SELECTED, this::updateTourLogList);
        eventAggregator.addSubscriber(Event.NEW_TOUR_LOG, this::onNewTourLog);
        eventAggregator.addSubscriber(Event.TOUR_LOG_MODIFIED, this::updateTourLogList);
        eventAggregator.addSubscriber(Event.SEARCH_ON, this::onSearch);
        eventAggregator.addSubscriber(Event.SEARCH_OFF, this::updateTourLogList);
    }

    private void enableButton(){
        selectedTour.set("");
    }
    private void updateTourLogList(){
        tourLogListView.clear();
        tourLogListView.addAll(tourLogService.findByTourName(getSelectedTourName()));
    }
    private void onNewTourLog() {
        tourLogListView.clear();
        tourLogListView.addAll(tourLogService.findByTourName(getSelectedTourName()));
    }
    private void onSearch() {
        tourLogListView.clear();
        tourLogListView.addAll(searchService.searchInTourLogs(searchService.getTextToSearch()));
    }

    public void deleteTourLog(String tourLogName){
        TourLog tourLog = tourLogService.findByName(tourLogName);
        if (tourLog != null) {
            tourLogService.delete(tourLogName);
            tourLogListView.remove(tourLogName);
        }
    }

    public void selectTourLog(String tourlog) {
        selectedTourLog.set(tourlog);
        selectedTourLogService.setTourLogName(tourlog);
        eventAggregator.publish(Event.TOUR_LOG_SELECTED);
        System.out.println(tourlog);
    }
    public String getSelectedTourName(){
        return selectedTourService.getTourName();
    }

    public ObservableList<String> getTourLogListView() {
        return tourLogListView;
    }

    public String getSelectedTourLog() {
        return selectedTourLog.get();
    }

    public StringProperty selectedTourLogProperty() {
        return selectedTourLog;
    }

    public void setSelectedTourLog(String selectedTourLog) {
        this.selectedTourLog.set(selectedTourLog);
    }

    public String getSelectedTour() {
        return selectedTour.get();
    }

    public StringProperty selectedTourProperty() {
        return selectedTour;
    }

    public void setSelectedTour(String selectedTour) {
        this.selectedTour.set(selectedTour);
    }
}