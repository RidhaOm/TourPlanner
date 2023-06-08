package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.AddTourLogViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTourLogView implements Initializable {

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
    private Button addTourLogButton;

    private final AddTourLogViewModel addTourLogViewModel;

    public AddTourLogView(AddTourLogViewModel addTourLogViewModel) {
        this.addTourLogViewModel = addTourLogViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind attributes to ViewModel properties
        datePicker.valueProperty().bindBidirectional(addTourLogViewModel.datePickerProperty());
        durationTextField.textProperty().bindBidirectional(addTourLogViewModel.durationTextFieldProperty());
        commentTextArea.textProperty().bindBidirectional(addTourLogViewModel.commentTextAreaProperty());

        // Initialize difficultyChoiceBox
        difficultyChoiceBox.getItems().addAll("1", "2", "3", "4", "5");
        difficultyChoiceBox.valueProperty().bindBidirectional(addTourLogViewModel.difficultyChoiceBoxProperty());

        // Initialize rankingChoiceBox
        rankingChoiceBox.getItems().addAll("1", "2", "3", "4", "5");
        rankingChoiceBox.valueProperty().bindBidirectional(addTourLogViewModel.rankingChoiceBoxProperty());

        // Set the DatePicker value to the current date
        datePicker.setValue(LocalDate.now());
    }

    public void addTourLog(){
        addTourLogViewModel.addTourLog();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
