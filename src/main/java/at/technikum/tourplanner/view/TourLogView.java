package at.technikum.tourplanner.view;
import at.technikum.tourplanner.FXMLDependencyInjector;
import at.technikum.tourplanner.viewModel.TourLogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class TourLogView implements Initializable {
    @FXML
    private ListView<String> tourLogListView;
    @FXML
    private Button addTourLogButton;
    @FXML
    private Button deleteTourLogButton;
    @FXML
    private Button modifyTourLogButton;

    private final TourLogViewModel tourLogViewModel;

    public TourLogView(TourLogViewModel tourLogViewModel){
        this.tourLogViewModel = tourLogViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourLogListView.setItems(tourLogViewModel.getTourLogListView());
        // Disable the modifyTourButton initially
        modifyTourLogButton.setDisable(true);
        deleteTourLogButton.setDisable(true);
        addTourLogButton.disableProperty().bind(tourLogViewModel.selectedTourProperty().isNull());


        // Listen to the selected tour name property and enable/disable the modifyTourButton accordingly
        tourLogViewModel.selectedTourLogProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                modifyTourLogButton.setDisable(false);
                deleteTourLogButton.setDisable(false);
            } else {
                modifyTourLogButton.setDisable(true);
                deleteTourLogButton.setDisable(true);
            }
        });
    }

    public void openAddTourLogWindow(ActionEvent event){
        try {
            Parent root = FXMLDependencyInjector.load("add-tour-log.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 600, 500);
            Stage stage = new Stage();
            stage.setTitle("Add Tour Log");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void openModifyTourLogWindow(ActionEvent event){
        try {
            Parent root = FXMLDependencyInjector.load("modify-tour-log.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 600, 500);
            Stage stage = new Stage();
            stage.setTitle("Modify Tour Log");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteTourLog(ActionEvent event) {
        String selectedTourName = tourLogListView.getSelectionModel().getSelectedItem();
        tourLogViewModel.deleteTourLog(selectedTourName);
    }

    public void selectTourLog() {
        String selectedTourLog = tourLogListView.getSelectionModel().getSelectedItem();
        tourLogViewModel.selectTourLog(selectedTourLog);
    }
}