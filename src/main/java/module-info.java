module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires opencsv;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;

    opens at.technikum.tourplanner to javafx.fxml, org.hibernate.orm.core;
    opens at.technikum.tourplanner.view to javafx.fxml;
    opens at.technikum.tourplanner.model to org.hibernate.orm.core;

    exports at.technikum.tourplanner;
}

