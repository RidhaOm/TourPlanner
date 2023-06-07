package at.technikum.tourplanner.view;

import at.technikum.tourplanner.FXMLDependencyInjector;
import at.technikum.tourplanner.viewModel.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class TourListView implements Initializable {

    @FXML
    private Button toursButton;

    @FXML
    private Button addTourButton;

    @FXML
    private Button deleteTourButton;

    @FXML
    private Button modifyTourButton;

    @FXML
    private ListView<String> tourListView;

    private final TourListViewModel tourListViewModel;

    public TourListView(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourListView.setItems(tourListViewModel.getTourListView());
        // Disable the modifyTourButton initially
        modifyTourButton.setDisable(true);

        // Listen to the selected tour name property and enable/disable the modifyTourButton accordingly
        tourListViewModel.selectedTourNameProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                modifyTourButton.setDisable(false);
            } else {
                modifyTourButton.setDisable(true);
            }
        });
    }


    public void openAddTourWindow(ActionEvent event){
        try {
            Parent root = FXMLDependencyInjector.load("add-tour.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 600, 300);
            Stage stage = new Stage();
            stage.setTitle("Add Tour");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void openModifyTourWindow(ActionEvent event){
        try {
            Parent root = FXMLDependencyInjector.load("modify-tour.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 600, 300);
            Stage stage = new Stage();
            stage.setTitle("Modify Tour");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteTour(ActionEvent event) {
        String selectedTourName = tourListView.getSelectionModel().getSelectedItem();
        tourListViewModel.deleteTour(selectedTourName);
    }

    @FXML
    public void selectTourName() {
        String selectedTourName = tourListView.getSelectionModel().getSelectedItem();
        tourListViewModel.selectTourName(selectedTourName);
    }


}