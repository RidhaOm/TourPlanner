package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.ModifyTourViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyTourView implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button modifyTourButton;
    @FXML
    private TextField tourNameTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private ChoiceBox<String> transportTypeChoiceBox;

    private String[] transportTypes = {"Car", "Walking","Bike"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourNameTextField.textProperty().bindBidirectional(editTourViewModel.tourNameTextFieldProperty());
        fromTextField.textProperty().bindBidirectional(editTourViewModel.fromTextFieldProperty());
        toTextField.textProperty().bindBidirectional(editTourViewModel.toTextFieldProperty());
        transportTypeChoiceBox.getItems().addAll(transportTypes);
        transportTypeChoiceBox.valueProperty().bindBidirectional(editTourViewModel.transportTypeChoiceBoxProperty());
        tourDescriptionTextArea.textProperty().bindBidirectional(editTourViewModel.tourDescriptionTextAreaProperty());
    }

    @FXML
    private TextArea tourDescriptionTextArea;

    private final ModifyTourViewModel editTourViewModel;
    public ModifyTourView(ModifyTourViewModel editTourViewModel) {
        this.editTourViewModel = editTourViewModel;
    }
    @FXML
    public void modifyTour(){
        editTourViewModel.editTour();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


}
