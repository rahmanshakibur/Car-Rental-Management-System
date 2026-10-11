package com.codecafe.car_rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// login page er controller class
public class LoginController {

    @FXML public PasswordField password;
    @FXML public TextField userName;

    private double x = 0, y = 0;

    // login button click korle ei method call hoy
    @FXML
    void LoginButton(ActionEvent event) {
        String uName = userName.getText().trim();
        String pwd = password.getText().trim();

        // faka field ache kina check kora
        if (uName.isEmpty() || pwd.isEmpty()) {
            showAlert("Error", "Please fill all information");
            return;
        }

        // database theke admin check korar query
        String sql = "SELECT * FROM codecafe_admin WHERE username = ? and password = ?";
        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uName);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // login successful hole username save kora
                GetData.username = uName;
                showAlert("Success", "Successfully Logged in!");

                // login stage bondho kora
                Stage currentStage = (Stage) userName.getScene().getWindow();
                currentStage.close();

                // dashboard open kora
                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                // mouse die window drag korar event
                root.setOnMousePressed((MouseEvent me) -> { x = me.getSceneX(); y = me.getSceneY(); });
                root.setOnMouseDragged((MouseEvent me) -> {
                    stage.setX(me.getScreenX() - x); stage.setY(me.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Error", "Wrong Username or Password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database Error: " + e.getMessage());
        }
    }

    // alert message dekhate ei method use hoy
    private void showAlert(String title, String msg) {
        Alert a = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}