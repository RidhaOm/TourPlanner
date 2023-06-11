package at.technikum.tourplanner.service;

import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.repository.TourLogRepository;
import at.technikum.tourplanner.repository.TourRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchService {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    private final EventAggregator eventAggregator;
    private String textToSearch;
    public SearchService(TourRepository tourRepository, TourLogRepository tourLogRepository, EventAggregator eventAggregator) {
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
        this.eventAggregator = eventAggregator;
    }
    public void search(String textToSearch){
        setTextToSearch(textToSearch);
        if (textToSearch==""){
            eventAggregator.publish(Event.SEARCH_OFF);
        }
        else {
            eventAggregator.publish(Event.SEARCH_ON);
        }
    }

public List<String> searchInTours(String textToSearch) {
    List<Tour> tours = tourRepository.search(textToSearch);
    List<String> tourNames = new ArrayList<>();
    for (Tour tour : tours) {
        tourNames.add(tour.getName());
    }
    return tourNames;
}

public List<String> searchInTourLogs(String textToSearch) {
    List<TourLog> tourLogs = tourLogRepository.search(textToSearch);
    List<String> tourLogNames = new ArrayList<>();
    for (TourLog tourLog : tourLogs) {
        tourLogNames.add(tourLog.getName());
    }
    return tourLogNames;
}
    public String getTextToSearch() {
        return textToSearch;
    }

    public void setTextToSearch(String textToSearch) {
        this.textToSearch = textToSearch;
    }
}
