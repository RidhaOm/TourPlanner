package at.technikum.tourplanner.Service;

import at.technikum.tourplanner.Model.TourRepository;

import java.util.List;

public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void save(String word) {
        tourRepository.save(word);
    }

    public List<String> findAll() {
        return tourRepository.findAll();
    }
}
