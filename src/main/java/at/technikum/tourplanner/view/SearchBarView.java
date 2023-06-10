package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.SearchBarViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarView implements Initializable {

    @FXML
    private TextField SearchTextField;
    @FXML
    private Button searchButton;

    private final SearchBarViewModel searchBarViewModel;

    public SearchBarView(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SearchTextField.textProperty().bindBidirectional(searchBarViewModel.searchTextFieldProperty());
    }
    public void search(){
        searchBarViewModel.search();
    }
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            searchBarViewModel.search();
        }
    }
}
