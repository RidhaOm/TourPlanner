package at.technikum.tourplanner.view;

import at.technikum.tourplanner.service.MapQuestRouteService;
import at.technikum.tourplanner.service.RouteService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import at.technikum.tourplanner.viewModel.AddTourViewModel;
import at.technikum.tourplanner.viewModel.NavigationBarViewModel;
import at.technikum.tourplanner.viewModel.TourDetailsViewModel;
import at.technikum.tourplanner.viewModel.TourListViewModel;
import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.repository.TourRepository;

public class ViewFactory {
    private static ViewFactory instance;

    private final EventAggregator eventAggregator;
    private final TourRepository tourRepository;
    private final RouteService routeService;
    private final AddTourViewModel addTourViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourService tourService;
    private final SelectedTourService selectedTourService;
    private final HibernateSessionFactory sessionFactory;
    private final NavigationBarViewModel navigationBarViewModel;

    private ViewFactory() {
        eventAggregator = new EventAggregator();
        sessionFactory = new HibernateSessionFactory();
        tourRepository = new TourRepository(sessionFactory, eventAggregator);
        tourService = new TourService(tourRepository);
        selectedTourService = new SelectedTourService();
        routeService = new MapQuestRouteService();
        addTourViewModel = new AddTourViewModel(routeService, tourService);
        tourListViewModel = new TourListViewModel(eventAggregator,tourService, selectedTourService);
        tourDetailsViewModel = new TourDetailsViewModel(eventAggregator,tourService, selectedTourService);
        navigationBarViewModel = new NavigationBarViewModel(tourService, selectedTourService);
    }

    public Object create(Class<?> viewClass) {
        if (viewClass == AddTourView.class) {
            return new AddTourView(addTourViewModel);
        }
        if (viewClass == TourListView.class) {
            return new TourListView(tourListViewModel);
        }
        if (viewClass == NavigationBarView.class) {
            return new NavigationBarView(navigationBarViewModel);
        }
        if (viewClass == TourDetailsView.class) {
            return new TourDetailsView(tourDetailsViewModel);
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
