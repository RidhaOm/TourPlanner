package at.technikum.tourplanner.view;

import at.technikum.tourplanner.Model.TourRepository;
import at.technikum.tourplanner.ViewModel.AddTourViewModel;
import at.technikum.tourplanner.ViewModel.TourListViewModel;

public class ViewFactory {
    private static ViewFactory instance;

    private final TourRepository tourRepository;
    private final AddTourViewModel addTourViewModel;
    private final TourListViewModel tourListViewModel;

    private ViewFactory() {
        tourRepository = new TourRepository();
        addTourViewModel = new AddTourViewModel(tourRepository);
        tourListViewModel = new TourListViewModel(tourRepository);
    }

    public Object create(Class<?> viewClass) {
        if (viewClass == AddTourView.class) {
            return new AddTourView(addTourViewModel);
        }
        if (viewClass == TourListView.class) {
            return new TourListView(tourListViewModel);
        }
        if (viewClass == MainView.class) {
            return new MainView();
        }
        if (viewClass == NavigationBarView.class) {
            return new NavigationBarView();
        }
        if (viewClass == SearchBarView.class) {
            return new SearchBarView();
        }
        if (viewClass == TourDetailsView.class) {
            return new TourDetailsView();
        }
        if (viewClass == TourLogView.class) {
            return new TourLogView();
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
