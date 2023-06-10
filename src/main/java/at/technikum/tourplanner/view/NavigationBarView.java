package at.technikum.tourplanner.view;

import at.technikum.tourplanner.FXMLDependencyInjector;
import at.technikum.tourplanner.viewModel.NavigationBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.Locale;

public class NavigationBarView {
    @FXML
    private MenuItem exportTour;
    @FXML
    private MenuItem importTour;
    @FXML
    private MenuItem reportTour;
    @FXML
    private MenuItem summaryReport;
    private final NavigationBarViewModel navigationBarViewModel;

    public NavigationBarView(NavigationBarViewModel navigationBarViewModel) {
        this.navigationBarViewModel = navigationBarViewModel;
    }

    public void exportTour(ActionEvent event) {
        navigationBarViewModel.exportTour();
    }
    public void importTour(ActionEvent event) {
        navigationBarViewModel.importTour();
    }
    public void reportTour(ActionEvent event) {
        navigationBarViewModel.generateTourReport();
    }

    public void summaryReport(ActionEvent event) {
        navigationBarViewModel.summaryReport();
    }

    public void openRecommendedToursWindow(ActionEvent event) {
        try {
            Parent root = FXMLDependencyInjector.load("recommended-tours.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 350, 400);
            Stage stage = new Stage();
            stage.setTitle("Recommended Tours");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
