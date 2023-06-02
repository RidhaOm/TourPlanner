package at.technikum.tourplanner.Model;

import at.technikum.tourplanner.event.NewTourEvent;

import java.util.ArrayList;
import java.util.List;

public class TourRepository {

    private final List<String> tours;

    private final List<NewTourEvent> newTourEvents;

    public TourRepository() {
        tours = new ArrayList<>();
        newTourEvents = new ArrayList<>();
    }

    private void notifyNewTourListeners(String word) {
        for (NewTourEvent nte: newTourEvents) {
            nte.newTour(word);
        }
    }

    public void addNewWordListener(NewTourEvent newTourEvent) {
        newTourEvents.add(newTourEvent);
    }


    public void save(String word) {
        tours.add(word);

        notifyNewTourListeners(word);
    }

    public List<String> findAll() {
        return tours;
    }

}
