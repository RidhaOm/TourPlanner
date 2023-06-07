package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.TourDetailsViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TourDetailsView {

    @FXML
    private ImageView mapView;

    @FXML
    private Label tourDetailsLabel;
    private final TourDetailsViewModel tourDetailsViewModel;

    public TourDetailsView(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    public void initialize() {
        mapView.imageProperty().bindBidirectional(tourDetailsViewModel.mapviewProperty());
        tourDetailsLabel.textProperty().bindBidirectional(tourDetailsViewModel.tourDetailsLabelProperty());
    }
    
    public void getSelectedTourName() {
        String name = tourDetailsViewModel.getSelectedTourName();
        System.out.println("item "+name+" selected");
    }
    public ImageView getMapView() {
        return mapView;
    }

    public void setMapView(ImageView mapView) {
        this.mapView = mapView;
    }

}
