module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires opencsv;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens at.technikum.tourplanner to javafx.fxml, org.hibernate.orm.core;
    opens at.technikum.tourplanner.view to javafx.fxml;
    opens at.technikum.tourplanner.model to org.hibernate.orm.core;

    exports at.technikum.tourplanner;
    exports at.technikum.tourplanner.dto to com.fasterxml.jackson.databind;

}

