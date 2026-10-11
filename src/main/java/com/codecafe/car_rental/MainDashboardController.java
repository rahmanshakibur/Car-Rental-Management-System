package com.codecafe.car_rental;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// main dashboard shell er controller (menu navigation)
public class MainDashboardController implements Initializable {

    @FXML private Label usernameLabel;
    @FXML private Button homeBtn, carsBtn, rentBtn, customersBtn, signOutBtn;
    @FXML private StackPane contentArea;

    // dashboard open hole username set hoy ar home view ashe
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (GetData.username != null && !GetData.username.isEmpty()) {
            usernameLabel.setText(GetData.username.substring(0, 1).toUpperCase() + GetData.username.substring(1));
        } else {
            usernameLabel.setText("Sakibur Rahman");
        }
        showHomeView();
    }

    // home view load korar button
    @FXML
    public void showHomeView() {
        setActiveButton(homeBtn);
        loadView("home-view.fxml");
    }

    // car management view load korar button
    @FXML
    public void showCarsView() {
        setActiveButton(carsBtn);
        loadView("cars-view.fxml");
    }

    // rent car view load korar button
    @FXML
    public void showRentView() {
        setActiveButton(rentBtn);
        loadView("rent-view.fxml");
    }

    // customer history view load korar button
    @FXML
    public void showCustomersView() {
        setActiveButton(customersBtn);
        loadView("customers-view.fxml");
    }

    // fxml file center area te load korar method
    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // active button er color change korar method
    private void setActiveButton(Button active) {
        homeBtn.getStyleClass().removeAll("nav-menu-btn-active", "nav-menu-btn");
        carsBtn.getStyleClass().removeAll("nav-menu-btn-active", "nav-menu-btn");
        rentBtn.getStyleClass().removeAll("nav-menu-btn-active", "nav-menu-btn");
        customersBtn.getStyleClass().removeAll("nav-menu-btn-active", "nav-menu-btn");

        homeBtn.getStyleClass().add("nav-menu-btn");
        carsBtn.getStyleClass().add("nav-menu-btn");
        rentBtn.getStyleClass().add("nav-menu-btn");
        customersBtn.getStyleClass().add("nav-menu-btn");

        active.getStyleClass().remove("nav-menu-btn");
        active.getStyleClass().add("nav-menu-btn-active");
    }

    // logout korar method
    @FXML
    public void handleSignOut() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Sign Out"); confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to sign out?");
        Optional<ButtonType> res = confirm.showAndWait();

        if (res.isPresent() && res.get() == ButtonType.OK) {
            try {
                Stage currentStage = (Stage) signOutBtn.getScene().getWindow();
                currentStage.close();

                // login window abar open kora
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                stage.setTitle("SK Car Rental House — Login");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    // window bondho korar method
    @FXML public void closeWindow() { System.exit(0); }

    // window minimize korar method
    @FXML public void minimizeWindow() {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        stage.setIconified(true);
    }
}
