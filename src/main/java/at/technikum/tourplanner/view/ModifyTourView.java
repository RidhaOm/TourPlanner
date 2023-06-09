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
        tourNameTextField.textProperty().bindBidirectional(modifyTourViewModel.tourNameTextFieldProperty());
        fromTextField.textProperty().bindBidirectional(modifyTourViewModel.fromTextFieldProperty());
        toTextField.textProperty().bindBidirectional(modifyTourViewModel.toTextFieldProperty());
        transportTypeChoiceBox.getItems().addAll(transportTypes);
        transportTypeChoiceBox.valueProperty().bindBidirectional(modifyTourViewModel.transportTypeChoiceBoxProperty());
        tourDescriptionTextArea.textProperty().bindBidirectional(modifyTourViewModel.tourDescriptionTextAreaProperty());
    }

    @FXML
    private TextArea tourDescriptionTextArea;

    private final ModifyTourViewModel modifyTourViewModel;
    public ModifyTourView(ModifyTourViewModel editTourViewModel) {
        this.modifyTourViewModel = editTourViewModel;
    }
    @FXML
    public void modifyTour(){
        modifyTourViewModel.editTour();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


}
