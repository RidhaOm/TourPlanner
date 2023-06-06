package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.event.Event;
import at.technikum.tourplanner.event.EventAggregator;
import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.PDFService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;

public class NavigationBarViewModel {
    private final TourService tourService;
    private final SelectedTourService selectedTourService;

    public NavigationBarViewModel(TourService tourService, SelectedTourService selectedTourService) {
        this.tourService = tourService;
        this.selectedTourService = selectedTourService;
    }

    public void deleteTour(String tourName) {
        Tour tour = tourService.findByName(tourName);
        if (tour != null) {
            tourService.delete(tourName);
        } else {
            System.out.println("Tour with name '" + tourName + "' does not exist.");
        }
    }

    public void exportTour() {
        String selectedTourName = selectedTourService.getTourName();
        if (selectedTourName != null) {
            Tour selectedTour = tourService.findByName(selectedTourName);
            if (selectedTour != null) {
                PDFService.export(selectedTour);
            } else {
                System.out.println("Tour with name '" + selectedTourName + "' does not exist.");
            }
        } else {
            System.out.println("No tour selected");
        }
    }
}
