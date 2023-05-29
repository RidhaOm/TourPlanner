package com.example.tourplanner.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddTourController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField tourNameTextField;

    @FXML
    private ListView<String> tourListView;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    // Reference to the TourListController
    private TourListController tourListController;

    public void setTourListController(TourListController tourListController) {
        this.tourListController = tourListController;
    }

    public void addTourToListView(String tourName) {
        ObservableList<String> items = tourListView.getItems();
        items.add(tourName);
    }

    public void saveTour(ActionEvent event) {
        String tourName = tourNameTextField.getText();

        System.out.println("Tour Name: " + tourName);

        // Add the tour name to the ListView
        addTourToListView(tourName);
        /*if (tourListController != null) {
            tourListController.addTourToListView(tourName);
        }*/

        // Get the stage (window) associated with the rootPane
        Stage stage = (Stage) rootPane.getScene().getWindow();

        // Close the window
        stage.close();
    }
}
