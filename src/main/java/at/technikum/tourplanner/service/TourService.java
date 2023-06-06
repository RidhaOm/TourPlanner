package at.technikum.tourplanner.service;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.repository.TourRepository;
import org.hibernate.Session;
//import at.technikum.tourplanner.Model.TourRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void save(String name, String tourFrom, String tourTo, Double distance, String time, String description, String transportType, String routeInformation ) {
        tourRepository.save(new Tour(name, tourFrom, tourTo, distance, time, description, transportType, routeInformation));
    }

    public void delete(String tourName) {
        tourRepository.delete(tourName);
    }
    public List<String> findAll() {
        return tourRepository.findAll()
                .stream()
                .map(Tour::getName)
                .collect(Collectors.toList());
    }
    public void selectTourName(String tourName) {
        tourRepository.selectTourName(tourName);
    }
    public Tour findById(Long id) {
        return tourRepository.findById(id);
    }
    public Tour findByName(String name) {
        return tourRepository.findByName(name);
    }
}
