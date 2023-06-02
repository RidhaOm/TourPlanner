package at.technikum.tourplanner;

import at.technikum.tourplanner.view.ViewFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLDependencyInjector {
    public static Parent load(String location, Locale locale) throws IOException {
        FXMLLoader loader = getLoader(location, locale);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location, Locale locale) {
        return new FXMLLoader(
                FXMLDependencyInjector.class.getResource("/at/technikum/tourplanner/" + location),
                ResourceBundle.getBundle("at.technikum.tourplanner.gui", locale),
                new JavaFXBuilderFactory(),
                viewClass -> ViewFactory.getInstance().create(viewClass)
        );
    }
}
