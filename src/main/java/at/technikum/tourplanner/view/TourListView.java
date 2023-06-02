package at.technikum.tourplanner.view;

import at.technikum.tourplanner.FXMLDependencyInjector;
import at.technikum.tourplanner.ViewModel.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;

public class TourListView {

    @FXML
    private Button toursButton;

    @FXML
    private Button addTourButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button moreButton;

    @FXML
    private ListView<String> tourListView;

    private final TourListViewModel tourListViewModel;

    public TourListView(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }


    public void initialize() {
        tourListView.setItems(tourListViewModel.getTourListView());
    }



    public void openAddTourWindow(ActionEvent event){
        try {
            Parent root = FXMLDependencyInjector.load("add-tour.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 500, 300);
            Stage stage = new Stage();
            stage.setTitle("Add Tour");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}