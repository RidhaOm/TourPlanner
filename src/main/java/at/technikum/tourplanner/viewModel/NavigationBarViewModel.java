package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.model.TourLog;
import at.technikum.tourplanner.service.PDFService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NavigationBarViewModel {
    private final TourService tourService;
    private final SelectedTourService selectedTourService;
    private static final Logger logger = Logger.getLogger(NavigationBarViewModel.class);


    public NavigationBarViewModel(TourService tourService, SelectedTourService selectedTourService) {
        this.tourService = tourService;
        this.selectedTourService = selectedTourService;
    }

    public void exportTour() {
        String selectedTourName = selectedTourService.getTourName();
        if (selectedTourName != null) {
            Tour selectedTour = tourService.findByName(selectedTourName);
            if (selectedTour != null) {
                PDFService.export(selectedTour);
                logger.info("Tour exported successfully: " + selectedTourName);
            } else {
                logger.error("Tour with name '" + selectedTourName + "' does not exist.");
                System.out.println("Tour with name '" + selectedTourName + "' does not exist.");
            }
        } else {
            logger.warn("No tour selected");
            System.out.println("No tour selected");
        }
    }

    public void importTour() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Tour");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Tour importedTour = PDFService.importTour(selectedFile);
                tourService.saveTour(importedTour);
                logger.info("Tour imported successfully.");
                System.out.println("Tour imported successfully.");
            } catch (IOException e) {
                logger.error("Error occurred while importing tour.", e);
                System.out.println("Error occurred while importing tour.");
                e.printStackTrace();
            }
        } else {
            logger.info("Tour import canceled.");
            System.out.println("Tour import canceled.");
        }
    }

    public void generateTourReport() {
        String selectedTourName = selectedTourService.getTourName();
        if (selectedTourName != null) {
            Tour selectedTour = tourService.findByName(selectedTourName);
            if (selectedTour != null) {
                List<TourLog> tourLogs = tourService.getTourLogs(selectedTour.getId());
                PDFService.generateTourReport(selectedTour, tourLogs);
                logger.info("Tour report generated successfully: " + selectedTourName);
            } else {
                logger.error("Tour with name '" + selectedTourName + "' does not exist.");
                System.out.println("Tour with name '" + selectedTourName + "' does not exist.");
            }
        } else {
            logger.warn("No tour selected");
            System.out.println("No tour selected");
        }
    }

    public void summaryReport() {
        List<Tour> allTours = tourService.getAllTours();
        List<TourLog> allLogs = new ArrayList<>();
        for (Tour tour : allTours) {
            allLogs.addAll(tourService.getTourLogs(tour.getId()));
        }
        PDFService.generateSummaryReport(allTours, allLogs);
    }

}
