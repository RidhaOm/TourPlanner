package at.technikum.tourplanner.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddTourController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField tourNameTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private MenuButton transportTypeMenuButton;

    @FXML
    private Label testLabel;

    public void saveTour(ActionEvent event){
        String tourName = tourNameTextField.getText();
        String from = fromTextField.getText();
        String to = toTextField.getText();

        testLabel.setText(tourName);
        System.out.println("Tour Name: " + tourName);
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        //System.out.println("Transport Type: " + transportType);

        // Get the stage (window) associated with the rootPane
        Stage stage = (Stage) rootPane.getScene().getWindow();

        // Close the window
        //stage.close();
    }
}