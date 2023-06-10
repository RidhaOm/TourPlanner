package at.technikum.tourplanner.service;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.repository.TourLogRepository;
import at.technikum.tourplanner.repository.TourRepository;

import java.util.List;

public class SearchService {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;
    public SearchService(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
    }
    public void search(String textToSearch){
        List<Tour> tours = tourRepository.search(textToSearch);
        for (Tour tour : tours) {
            System.out.println("------Tour: " + tour.getName() + "\n");
        }

        List<TourLog> tourLogs = tourLogRepository.search(textToSearch);
        for (TourLog tourLog : tourLogs) {
            System.out.println("------TourLog: " + tourLog.getName() + "\n");
        }
    }
}
