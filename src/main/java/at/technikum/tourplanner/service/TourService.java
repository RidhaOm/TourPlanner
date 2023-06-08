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

    public void save(String name, String tourFrom, String tourTo, Double distance, String time, String description, String transportType ) {
        tourRepository.save(new Tour(name, tourFrom, tourTo, distance, time, description, transportType));
    }

    public void delete(String tourName) {
        tourRepository.delete(tourName);
    }

    public void modify(String existingTourName, String name, String tourFrom, String tourTo, Double distance, String time, String description, String transportType ){
        Tour newTour = new Tour(name, tourFrom, tourTo, distance, time, description, transportType);
        tourRepository.modify(existingTourName, newTour);
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
    public String getTourDetailsByName(String name) {
        String tourDetails = "";
        Tour tour = findByName(name);
        tourDetails += "Name: " + tour.getName()+"\n";
        tourDetails += "From: " + tour.getTourFrom()+"\n";
        tourDetails += "To: " + tour.getTourTo()+"\n";
        tourDetails += "Transport type: " + tour.getTransportType()+"\n";
        tourDetails += "Time: " + tour.getTime()+"\n";
        tourDetails += "Distance: " + tour.getDistance()+"\n";
        tourDetails += "Description: " + tour.getDescription()+"\n";
        return tourDetails;
    }
    public void saveTour(Tour tour) {
        tourRepository.save(tour);
    }
}
