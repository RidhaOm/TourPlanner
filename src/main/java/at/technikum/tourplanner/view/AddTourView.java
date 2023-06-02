package at.technikum.tourplanner.view;

import at.technikum.tourplanner.ViewModel.AddTourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AddTourView {

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

    @FXML
    private ListView<String> testList;

    private final AddTourViewModel addTourViewModel;

    public AddTourView(AddTourViewModel addTourViewModel) {
        this.addTourViewModel=addTourViewModel;
    }

    @FXML
    void initialize() {
        tourNameTextField.textProperty().bindBidirectional(addTourViewModel.tourNameTextFieldProperty());
        fromTextField.textProperty().bindBidirectional(addTourViewModel.fromTextFieldProperty());
        toTextField.textProperty().bindBidirectional(addTourViewModel.toTextFieldProperty());
        testLabel.textProperty().bind(addTourViewModel.testLabelProperty());
        testList.setItems(addTourViewModel.getTestList());
    }

    public void saveTour(){
        addTourViewModel.saveTour();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        //stage.close();
    }
}