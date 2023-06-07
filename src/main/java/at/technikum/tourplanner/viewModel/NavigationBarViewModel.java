package at.technikum.tourplanner.viewModel;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.service.PDFService;
import at.technikum.tourplanner.service.SelectedTourService;
import at.technikum.tourplanner.service.TourService;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class NavigationBarViewModel {
    private final TourService tourService;
    private final SelectedTourService selectedTourService;

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
            } else {
                System.out.println("Tour with name '" + selectedTourName + "' does not exist.");
            }
        } else {
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
                System.out.println("Tour imported successfully.");
            } catch (IOException e) {
                System.out.println("Error occurred while importing tour.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Tour import canceled.");
        }
    }


}
