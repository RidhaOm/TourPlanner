package at.technikum.tourplanner.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TourDetailsView {
    @FXML
    private ImageView mapView;

    @FXML
    private void reloadImage() {
        try {
            // Reload the image here
            //Image image = new Image(getClass().getResourceAsStream("/at/technikum/tourplanner/maps/map.jpg"));
            Image image = new Image("file:maps/map.jpg");
            mapView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}