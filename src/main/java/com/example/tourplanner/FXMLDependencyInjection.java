package com.example.tourplanner;

import com.example.tourplanner.Controllers.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;

public class FXMLDependencyInjection {
    public static Parent load(String location, Locale locale) throws IOException {
        FXMLLoader loader = getLoader(location, locale);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location, Locale locale) {
        return new FXMLLoader(

                FXMLDependencyInjection.class.getResource(location),
                null,//ResourceBundle.getBundle("com.example.tourplanner" + "gui_strings", locale),
                new JavaFXBuilderFactory(),
                controllerClass -> ControllerFactory.getinstance().create(controllerClass)
        );
    }
}
