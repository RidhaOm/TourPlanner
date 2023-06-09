package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.service.RecommendedTourService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RecommendedToursViewModel {

    private final ObservableList<String> recommendedToursListView = FXCollections.observableArrayList();
    private final EventAggregator eventAggregator;
    private final RecommendedTourService recommendedTourService;
    public RecommendedToursViewModel(EventAggregator eventAggregator, RecommendedTourService recommendedTourService){
        this.eventAggregator = eventAggregator;
        this.recommendedTourService = recommendedTourService;
        recommendedToursListView.addAll(recommendedTourService.findRecommendedTours());

        eventAggregator.addSubscriber(Event.NEW_TOUR, this::updateRecommendedToursListView);
        eventAggregator.addSubscriber(Event.TOUR_DELETED, this::updateRecommendedToursListView);
        eventAggregator.addSubscriber(Event.NEW_TOUR_LOG, this::updateRecommendedToursListView);
        eventAggregator.addSubscriber(Event.TOUR_LOG_MODIFIED, this::updateRecommendedToursListView);
        eventAggregator.addSubscriber(Event.TOUR_LOG_DELETED, this::updateRecommendedToursListView);


    }
    private void updateRecommendedToursListView() {
        recommendedToursListView.clear();
        recommendedToursListView.addAll(recommendedTourService.findRecommendedTours());
    }

    public ObservableList<String> getRecommendedToursListView() {
        return recommendedToursListView;
    }
}
