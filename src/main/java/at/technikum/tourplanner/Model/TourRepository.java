package at.technikum.tourplanner.Model;

import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.event.NewTourEvent;

import java.util.ArrayList;
import java.util.List;

public class TourRepository {

    private final List<String> tours;

    private final EventAggregator eventAggregator;

    public TourRepository(EventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;
        tours = new ArrayList<>();
    }


    public void save(String word) {
        tours.add(word);
        eventAggregator.publish(Event.NEW_TOUR);

    }

    public List<String> findAll() {
        return tours;
    }

}
