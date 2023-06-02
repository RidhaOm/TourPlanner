package at.technikum.tourplanner.ViewModel;

import at.technikum.tourplanner.Model.TourRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {
    private final ObservableList<String> tourListView = FXCollections.observableArrayList();

    private final TourRepository tourRepository;

    public TourListViewModel(TourRepository tourRepository) {
        this.tourRepository = tourRepository;

        tourListView.addAll(tourRepository.findAll());

        tourRepository.addNewWordListener(this::addNewTour);
    }

    private void addNewTour(String word) {
        tourListView.add(word);
    }

    public ObservableList<String> getTourListView() {
        return tourListView;
    }
}
