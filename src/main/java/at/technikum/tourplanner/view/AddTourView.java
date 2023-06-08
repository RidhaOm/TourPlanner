package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.AddTourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddTourView implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField tourNameTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private ChoiceBox<String> transportTypeChoiceBox;

    private String[] transportTypes = {"Car", "Walking","Bike"};

    @FXML
    private TextArea tourDescriptionTextArea;


    private final AddTourViewModel addTourViewModel;

    public AddTourView(AddTourViewModel addTourViewModel) {
        this.addTourViewModel = addTourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourNameTextField.textProperty().bindBidirectional(addTourViewModel.tourNameTextFieldProperty());
        fromTextField.textProperty().bindBidirectional(addTourViewModel.fromTextFieldProperty());
        toTextField.textProperty().bindBidirectional(addTourViewModel.toTextFieldProperty());
        transportTypeChoiceBox.getItems().addAll(transportTypes);
        transportTypeChoiceBox.valueProperty().bindBidirectional(addTourViewModel.transportTypeChoiceBoxProperty());
        tourDescriptionTextArea.textProperty().bindBidirectional(addTourViewModel.tourDescriptionTextAreaProperty());
    }

    public void saveTour() {
        addTourViewModel.saveTour();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}
