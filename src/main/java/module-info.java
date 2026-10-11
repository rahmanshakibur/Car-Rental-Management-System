module com.codecafe.car_rental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires fontawesomefx;

    opens com.codecafe.car_rental to javafx.fxml, javafx.base;
    exports com.codecafe.car_rental;
}