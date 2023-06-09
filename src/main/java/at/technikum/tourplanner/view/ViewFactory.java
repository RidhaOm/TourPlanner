package at.technikum.tourplanner.view;

import at.technikum.tourplanner.repository.TourLogRepository;
import at.technikum.tourplanner.service.*;
import at.technikum.tourplanner.viewModel.*;
import at.technikum.tourplanner.data.HibernateSessionFactory;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.repository.TourRepository;

public class ViewFactory {
    private static ViewFactory instance;

    private final EventAggregator eventAggregator;
    private final TourRepository tourRepository;
    private final RouteService routeService;
    private final AddTourViewModel addTourViewModel;
    private final ModifyTourViewModel editTourViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final RecommendedToursViewModel recommendedTourViewModel;
    private final TourService tourService;
    private final SelectedTourService selectedTourService;
    private final SelectedTourLogService selectedTourLogService;
    private final RecommendedTourService recommendedTourService;
    private final HibernateSessionFactory sessionFactory;
    private final NavigationBarViewModel navigationBarViewModel;
    private final TourLogViewModel tourLogViewModel;
    private final AddTourLogViewModel addTourLogViewModel;
    private final TourLogService tourLogService;
    private final TourLogRepository tourLogRepository;
    private final ModifyTourLogViewModel modifyTourLogViewModel;

    private ViewFactory() {
        eventAggregator = new EventAggregator();
        sessionFactory = new HibernateSessionFactory();
        tourRepository = new TourRepository(sessionFactory, eventAggregator);
        tourLogRepository = new TourLogRepository(sessionFactory, eventAggregator, tourRepository);
        tourService = new TourService(tourRepository, tourLogRepository);
        selectedTourService = new SelectedTourService();
        selectedTourLogService = new SelectedTourLogService();
        routeService = new MapQuestRouteService();
        recommendedTourService = new RecommendedTourService(tourRepository, tourLogRepository);
        addTourViewModel = new AddTourViewModel(routeService, tourService);
        editTourViewModel = new ModifyTourViewModel(eventAggregator, tourService, routeService, selectedTourService);
        tourListViewModel = new TourListViewModel(eventAggregator,tourService, selectedTourService);
        tourDetailsViewModel = new TourDetailsViewModel(eventAggregator,tourService, selectedTourService);
        navigationBarViewModel = new NavigationBarViewModel(tourService, selectedTourService);
        tourLogService = new TourLogService(tourLogRepository);
        tourLogViewModel = new TourLogViewModel(eventAggregator, tourLogService, selectedTourService, selectedTourLogService);
        addTourLogViewModel = new AddTourLogViewModel(tourLogService, selectedTourService);
        modifyTourLogViewModel = new ModifyTourLogViewModel(eventAggregator, tourLogService, selectedTourLogService, selectedTourService);
        recommendedTourViewModel = new RecommendedToursViewModel(eventAggregator, recommendedTourService);
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
        if (viewClass == AddTourLogView.class) {
            return new AddTourLogView(addTourLogViewModel);
        }
        if (viewClass == ModifyTourView.class) {
            return new ModifyTourView(editTourViewModel);
        }
        if (viewClass == TourLogView.class) {
            return new TourLogView(tourLogViewModel);
        }
        if (viewClass == ModifyTourLogView.class) {
            return new ModifyTourLogView(modifyTourLogViewModel);
        }
        if (viewClass == RecommendedToursView.class) {
            return new RecommendedToursView(recommendedTourViewModel);
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
