module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires opencsv;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.example.tourplanner to javafx.fxml;
    opens com.example.tourplanner.Controllers to javafx.fxml;
    exports com.example.tourplanner;
}