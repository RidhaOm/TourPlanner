package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.ModifyTourLogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifyTourLogView implements Initializable {
    @FXML
    private BorderPane rootPane;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField durationTextField;

    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    private ChoiceBox<String> rankingChoiceBox;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button modifyTourLogButton;
    private final ModifyTourLogViewModel modifyTourLogViewModel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind attributes to ViewModel properties
        datePicker.valueProperty().bindBidirectional(modifyTourLogViewModel.datePickerProperty());
        durationTextField.textProperty().bindBidirectional(modifyTourLogViewModel.durationTextFieldProperty());
        commentTextArea.textProperty().bindBidirectional(modifyTourLogViewModel.commentTextAreaProperty());

        // Initialize difficultyChoiceBox
        difficultyChoiceBox.getItems().addAll("1", "2", "3", "4", "5");
        difficultyChoiceBox.valueProperty().bindBidirectional(modifyTourLogViewModel.difficultyChoiceBoxProperty());

        // Initialize rankingChoiceBox
        rankingChoiceBox.getItems().addAll("1", "2", "3", "4", "5");
        rankingChoiceBox.valueProperty().bindBidirectional(modifyTourLogViewModel.rankingChoiceBoxProperty());

        // Set the DatePicker value to the current date
        datePicker.setValue(LocalDate.now());
    }
    public ModifyTourLogView(ModifyTourLogViewModel modifyTourLogViewModel) {
        this.modifyTourLogViewModel = modifyTourLogViewModel;
    }
    public void modifyTourLog() {
        modifyTourLogViewModel.modifyTourLog();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


}
