package at.technikum.tourplanner.service;

import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.repository.TourLogRepository;
import java.util.List;
import java.util.stream.Collectors;

public class TourLogService {
    private final TourLogRepository tourLogRepository;
    public TourLogService(TourLogRepository tourLogRepository){
        this.tourLogRepository=tourLogRepository;
    }

    public void saveTourLog(String tourName, String date, double duration, int difficulty, int ranking, String comment) {
        TourLog tourLog = new TourLog(tourName, date, duration, difficulty, ranking, comment);
        tourLogRepository.save(tourLog);
    }

    public void delete(String name) {
        tourLogRepository.delete(name);
    }

    public void modify(String existingName, String tourName, String date, double duration, int difficulty, int ranking, String comment){
        TourLog newTourLog = new TourLog(tourName, date, duration, difficulty, ranking, comment);
        tourLogRepository.modify(existingName, newTourLog);
    }
    public List<String> findAll() {
        return tourLogRepository.findAll()
                .stream()
                .map(TourLog::getTourLog)
                .collect(Collectors.toList());
    }
    public List<String> findByTourName(String tourName) {
        return tourLogRepository.findByTourName(tourName)
                .stream()
                .map(TourLog::getTourLog)
                .collect(Collectors.toList());
    }
    public TourLog findByName(String name) {
        return tourLogRepository.findByName(name);
    }
}
