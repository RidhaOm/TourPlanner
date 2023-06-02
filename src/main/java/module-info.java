module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires opencsv;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens at.technikum.tourplanner to javafx.fxml;
    opens at.technikum.tourplanner.Controllers to javafx.fxml;
    exports at.technikum.tourplanner;
}