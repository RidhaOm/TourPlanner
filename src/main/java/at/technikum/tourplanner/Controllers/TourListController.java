package at.technikum.tourplanner.Controllers;

import at.technikum.tourplanner.FXMLDependencyInjection;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class TourListController {

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
}