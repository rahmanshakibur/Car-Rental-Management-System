package com.codecafe.car_rental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// javafx application load korar class
public class HelloApplication extends Application {
    // start method
    @Override
    public void start(Stage stage) throws IOException {
        // login page load kora hocche
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SK Car Rental House — Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
