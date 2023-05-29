package com.example.tourplanner.Controllers;

import com.example.tourplanner.FXMLDependencyInjection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Locale;

public class TourListController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ListView<String> tourListView;

    public void openAddTourWindow(ActionEvent event){
        try {
            Parent root = FXMLDependencyInjection.load("add-tour.fxml", Locale.GERMAN);
            Scene scene = new Scene(root, 500, 300);
            Stage stage = new Stage();
            stage.setTitle("Add Tour");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addTourToListView(String tourName) {
        ObservableList<String> items = tourListView.getItems();
        items.add(tourName);
    }
}
