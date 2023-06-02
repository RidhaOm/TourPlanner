package at.technikum.tourplanner.view;

import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.ViewModel.AddTourViewModel;
import at.technikum.tourplanner.ViewModel.TourListViewModel;
import at.technikum.tourplanner.event.EventAggregator;

public class ViewFactory {
    private static ViewFactory instance;

    private final EventAggregator eventAggregator;
    private final TourRepository tourRepository;
    private final AddTourViewModel addTourViewModel;
    private final TourListViewModel tourListViewModel;

    private ViewFactory() {
        eventAggregator = new EventAggregator();
        tourRepository = new TourRepository(eventAggregator);
        addTourViewModel = new AddTourViewModel(tourRepository);
        tourListViewModel = new TourListViewModel(eventAggregator,tourRepository);
    }

    public Object create(Class<?> viewClass) {
        if (viewClass == AddTourView.class) {
            return new AddTourView(addTourViewModel);
        }
        if (viewClass == TourListView.class) {
            return new TourListView(tourListViewModel);
        }
        if (viewClass == NavigationBarView.class) {
            return new NavigationBarView();
        }
        if (viewClass == TourDetailsView.class) {
            return new TourDetailsView();
        }
        if (viewClass == SearchBarView.class) {
            return new SearchBarView();
        }
        if (viewClass == TourLogView.class) {
            return new TourLogView();
        }
        if (viewClass == MainView.class) {
            return new MainView();
        }

        throw new IllegalArgumentException();
    }


    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }
        return instance;
    }
}
