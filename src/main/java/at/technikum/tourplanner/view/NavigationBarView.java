package at.technikum.tourplanner.view;

import at.technikum.tourplanner.model.Tour;
import at.technikum.tourplanner.viewModel.NavigationBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class NavigationBarView {
    @FXML
    private MenuItem exportTour;

    private final NavigationBarViewModel navigationBarViewModel;

    public NavigationBarView(NavigationBarViewModel navigationBarViewModel) {
        this.navigationBarViewModel = navigationBarViewModel;
    }

    public void exportTour(ActionEvent event) {
        navigationBarViewModel.exportTour();
    }
}
