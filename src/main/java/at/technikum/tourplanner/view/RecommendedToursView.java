package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.RecommendedToursViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecommendedToursView implements Initializable {
    @FXML
    private ListView<String> recommendedToursListView;
    private final RecommendedToursViewModel recommendedToursViewModel;
    public RecommendedToursView(RecommendedToursViewModel recommendedTourViewModel) {
        this.recommendedToursViewModel = recommendedTourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recommendedToursListView.setItems(recommendedToursViewModel.getRecommendedToursListView());
    }
}
