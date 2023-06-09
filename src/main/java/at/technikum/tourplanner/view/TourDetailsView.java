package at.technikum.tourplanner.view;

import at.technikum.tourplanner.viewModel.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;


public class TourDetailsView {

    @FXML
    private ImageView mapView;

    @FXML
    private Label tourDetailsLabel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private static final Logger logger = Logger.getLogger(TourDetailsView.class);


    public TourDetailsView(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    public void initialize() {
        mapView.imageProperty().bindBidirectional(tourDetailsViewModel.mapviewProperty());
        tourDetailsLabel.textProperty().bindBidirectional(tourDetailsViewModel.tourDetailsLabelProperty());
    }
    
    public void getSelectedTourName() {
        String name = tourDetailsViewModel.getSelectedTourName();
        logger.info("Selected tour: " + name);
    }
    public ImageView getMapView() {
        return mapView;
    }

    public void setMapView(ImageView mapView) {
        this.mapView = mapView;
    }

}
