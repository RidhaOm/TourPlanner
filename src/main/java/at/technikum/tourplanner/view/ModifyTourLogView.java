package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.ModifyTourLogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

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

    }
    public ModifyTourLogView(ModifyTourLogViewModel modifyTourLogViewModel) {
        this.modifyTourLogViewModel = modifyTourLogViewModel;
    }
    public void modifyTourLog(ActionEvent event) {
    }


}
