package at.technikum.tourplanner.service;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.repository.TourLogRepository;
import at.technikum.tourplanner.repository.TourRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendedTourService {
    private final TourRepository tourRepository;
    private final TourLogRepository tourLogRepository;

    public RecommendedTourService(TourRepository tourRepository, TourLogRepository tourLogRepository) {

        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;
    }

    public List<String> findRecommendedTours() {
        List<Tour> recommendedTours = tourRepository.findAll()
                .stream()
                .filter(tour -> tour.getPopularity() > 0)
                .filter(tour -> calculateAverageRanking(tour) < 2.5)
                .sorted(Comparator.comparingDouble(this::calculateAverageRanking))
                .collect(Collectors.toList());

        return recommendedTours.stream()
                .map(Tour::getName)
                .collect(Collectors.toList());
    }

    private double calculateAverageRanking(Tour tour) {
        List<TourLog> tourLogs = tourLogRepository.findByTourName(tour.getName());
        if (tourLogs.isEmpty()) {
            return 0.0;
        }
        double totalRanking = tourLogs.stream()
                .mapToInt(TourLog::getRanking)
                .sum();
        return totalRanking / tourLogs.size();
    }

}
