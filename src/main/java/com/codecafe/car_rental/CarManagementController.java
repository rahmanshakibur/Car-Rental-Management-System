package com.codecafe.car_rental;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

// car manage korar controller class
public class CarManagementController implements Initializable {

    @FXML private TextField searchField, brandField, modelField, carIdField, priceField;
    @FXML private TableView<CarData> carTableView;
    @FXML private TableColumn<CarData, Integer> colCarId;
    @FXML private TableColumn<CarData, String> colBrand, colModel, colStatus;
    @FXML private TableColumn<CarData, Double> colPrice;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ImageView carImageView;

    private ObservableList<CarData> carList;
    private String selectedImagePath = "";

    // page load hole eita age run hoy
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusComboBox.setItems(FXCollections.observableArrayList("Available", "Not Available"));
        showCarsInTable();
    }

    // table e car show korar method
    public void showCarsInTable() {
        carList = CarDatabaseHelper.getAllCars();
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carTableView.setItems(carList);
    }

    // table e click korle data textfield e boshbe
    @FXML
    public void selectCarFromTable() {
        CarData car = carTableView.getSelectionModel().getSelectedItem();
        if (car == null) return;

        carIdField.setText(String.valueOf(car.getCarId()));
        brandField.setText(car.getBrand());
        modelField.setText(car.getModel());
        priceField.setText(String.valueOf(car.getPrice()));
        statusComboBox.setValue(car.getStatus());
        selectedImagePath = car.getImage();

        // car er image thakle imageview te set kora
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            File f = new File(selectedImagePath);
            if (f.exists()) carImageView.setImage(new Image(f.toURI().toString(), 120, 90, false, true));
            else carImageView.setImage(null);
        } else {
            carImageView.setImage(null);
        }
    }

    // computer theke car image select korar method
    @FXML
    public void chooseCarImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Image");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        File f = chooser.showOpenDialog(brandField.getScene().getWindow());
        if (f != null) {
            selectedImagePath = f.getAbsolutePath();
            carImageView.setImage(new Image(f.toURI().toString(), 120, 90, false, true));
        }
    }

    // notun car add korar method
    @FXML
    public void addCar() {
        if (carIdField.getText().isEmpty() || brandField.getText().isEmpty()
                || modelField.getText().isEmpty() || priceField.getText().isEmpty()
                || statusComboBox.getValue() == null) {
            showAlert("Error", "Please fill all fields.");
            return;
        }

        boolean ok = CarDatabaseHelper.insertCar(
                Integer.parseInt(carIdField.getText().trim()), brandField.getText().trim(),
                modelField.getText().trim(), Double.parseDouble(priceField.getText().trim()),
                statusComboBox.getValue(), selectedImagePath
        );

        if (ok) {
            showAlert("Success", "Car added successfully!");
            showCarsInTable();
            clearFields();
        } else {
            showAlert("Error", "Could not add car. ID might be duplicate.");
        }
    }

    // car er information update korar method
    @FXML
    public void updateCar() {
        if (carIdField.getText().isEmpty()) { showAlert("Error", "Select a car first."); return; }

        boolean ok = CarDatabaseHelper.updateCar(
                Integer.parseInt(carIdField.getText().trim()), brandField.getText().trim(),
                modelField.getText().trim(), Double.parseDouble(priceField.getText().trim()),
                statusComboBox.getValue(), selectedImagePath
        );

        if (ok) {
            showAlert("Success", "Car updated successfully!");
            showCarsInTable();
            clearFields();
        } else {
            showAlert("Error", "Update failed.");
        }
    }

    // car delete korar method
    @FXML
    public void deleteCar() {
        if (carIdField.getText().isEmpty()) { showAlert("Error", "Select a car first."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm"); confirm.setHeaderText(null);
        confirm.setContentText("Delete Car ID: " + carIdField.getText() + "?");
        Optional<ButtonType> res = confirm.showAndWait();

        if (res.isPresent() && res.get() == ButtonType.OK) {
            if (CarDatabaseHelper.deleteCar(Integer.parseInt(carIdField.getText().trim()))) {
                showAlert("Success", "Car deleted successfully!");
                showCarsInTable();
                clearFields();
            }
        }
    }

    // shob field faka korar method
    @FXML
    public void clearFields() {
        carIdField.setText(""); brandField.setText("");
        modelField.setText(""); priceField.setText("");
        statusComboBox.setValue(null); selectedImagePath = "";
        carImageView.setImage(null);
    }

    // table theke car search korar method
    @FXML
    public void searchCar() {
        FilteredList<CarData> filter = new FilteredList<>(carList, e -> true);
        searchField.textProperty().addListener((obs, oldV, newV) -> {
            filter.setPredicate(car -> {
                if (newV == null || newV.isEmpty()) return true;
                String k = newV.toLowerCase().trim();
                return String.valueOf(car.getCarId()).contains(k)
                        || car.getBrand().toLowerCase().contains(k)
                        || car.getModel().toLowerCase().contains(k);
            });
        });
        SortedList<CarData> sorted = new SortedList<>(filter);
        sorted.comparatorProperty().bind(carTableView.comparatorProperty());
        carTableView.setItems(sorted);
    }

    // alert message dekhate ei method use hoy
    private void showAlert(String title, String msg) {
        Alert a = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
