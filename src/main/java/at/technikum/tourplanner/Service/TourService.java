package at.technikum.tourplanner.Service;

import at.technikum.tourplanner.Model.Tour;
import at.technikum.tourplanner.repository.TourRepository;
//import at.technikum.tourplanner.Model.TourRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void save(String word) {
        tourRepository.save(new Tour(word));
    }

    public List<String> findAll() {
        return tourRepository.findAll()
                .stream()
                .map(Tour::getValue)
                .collect(Collectors.toList());
    }
}
