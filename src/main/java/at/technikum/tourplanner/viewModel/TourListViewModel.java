package at.technikum.tourplanner.viewModel;

//import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.SearchService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.repository.TourRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {
    private final ObservableList<String> tourListView = FXCollections.observableArrayList();

    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final SelectedTourService selectedTourService;
    private final SearchService searchService;

    private final StringProperty selectedTourName = new SimpleStringProperty();


    public TourListViewModel(EventAggregator eventAggregator, TourService tourService, SelectedTourService selectedTourService, SearchService searchService) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.selectedTourService=selectedTourService;
        this.searchService = searchService;

        tourListView.addAll(tourService.findAll());

        eventAggregator.addSubscriber(Event.NEW_TOUR, this::onNewTour);
        eventAggregator.addSubscriber(Event.SEARCH_ON, this::onSearch);
        eventAggregator.addSubscriber(Event.SEARCH_OFF, this::onNewTour);
    }

    private void onNewTour() {
        tourListView.clear();
        tourListView.addAll(tourService.findAll());
    }
    private void onSearch() {
        tourListView.clear();
        tourListView.addAll(searchService.searchInTours(searchService.getTextToSearch()));
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
        selectedTourName.set(tourName);
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

    public String getSelectedTourName() {
        return selectedTourName.get();
    }

    public StringProperty selectedTourNameProperty() {
        return selectedTourName;
    }

    public void setSelectedTourName(String selectedTourName) {
        this.selectedTourName.set(selectedTourName);
    }

}
