package com.codecafe.car_rental;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

// car rent korar main controller
public class RentCarController implements Initializable {

    @FXML private TableView<CarData> availableCarsTableView;
    @FXML private TableColumn<CarData, Integer> colCarId;
    @FXML private TableColumn<CarData, String> colBrand, colModel;
    @FXML private TableColumn<CarData, Double> colPrice;

    @FXML private ComboBox<String> carIdComboBox, genderComboBox;
    @FXML private TextField brandField, modelField, firstNameField, lastNameField, amountPaidField;
    @FXML private DatePicker dateRentedPicker, dateReturnPicker;
    @FXML private Label rentalDaysLabel, totalCostLabel, balanceLabel;

    private double dailyPrice = 0.0;
    private double finalTotalCost = 0.0;

    // page open hole eita prothome run hoy
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        refreshCarList();
    }

    // table ar dropdown refresh korar method
    public void refreshCarList() {
        var cars = RentalDatabaseHelper.getAvailableCars();
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableCarsTableView.setItems(cars);

        var ids = FXCollections.<String>observableArrayList();
        for (CarData c : cars) ids.add(String.valueOf(c.getCarId()));
        carIdComboBox.setItems(ids);
    }

    // table theke car select korle field fillup hoy
    @FXML
    public void selectCarFromTable() {
        CarData car = availableCarsTableView.getSelectionModel().getSelectedItem();
        if (car == null) return;
        carIdComboBox.setValue(String.valueOf(car.getCarId()));
        brandField.setText(car.getBrand());
        modelField.setText(car.getModel());
        dailyPrice = car.getPrice();
        calculateTotalCost();
    }

    // dropdown theke car id select korar method
    @FXML
    public void onCarIdSelected() {
        String idStr = carIdComboBox.getValue();
        if (idStr == null) return;
        CarData car = RentalDatabaseHelper.getCarById(Integer.parseInt(idStr));
        if (car != null) {
            brandField.setText(car.getBrand());
            modelField.setText(car.getModel());
            dailyPrice = car.getPrice();
            calculateTotalCost();
        }
    }

    // din ar total taka hisab kora hocche
    @FXML
    public void calculateTotalCost() {
        if (dateRentedPicker.getValue() == null || dateReturnPicker.getValue() == null) return;
        int days = RentCalculationHelper.calculateDays(dateRentedPicker.getValue(), dateReturnPicker.getValue());
        rentalDaysLabel.setText(days + (days > 1 ? " Days" : " Day"));
        finalTotalCost = RentCalculationHelper.calculateTotal(dailyPrice, days);
        totalCostLabel.setText("$" + String.format("%.2f", finalTotalCost));
        calculateBalance();
    }

    // customer ke koto taka ferot dite hobe tar hisab
    @FXML
    public void calculateBalance() {
        String paid = amountPaidField.getText().trim();
        if (paid.isEmpty()) { balanceLabel.setText("$0.00"); return; }
        try {
            double bal = RentCalculationHelper.calculateBalance(Double.parseDouble(paid), finalTotalCost);
            balanceLabel.setText("$" + String.format("%.2f", bal));
        } catch (Exception e) { balanceLabel.setText("$0.00"); }
    }

    // booking confirm kore database e save kora hocche
    @FXML
    public void confirmRental() {
        if (carIdComboBox.getValue() == null || firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty() || genderComboBox.getValue() == null
                || dateRentedPicker.getValue() == null || dateReturnPicker.getValue() == null
                || amountPaidField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all booking details.");
            return;
        }

        double paid = Double.parseDouble(amountPaidField.getText().trim());
        if (paid < finalTotalCost) {
            showAlert("Error", "Amount paid cannot be less than total rent!");
            return;
        }

        // database e save kora hocche helper class die
        boolean ok = RentalDatabaseHelper.saveRentalBooking(
                firstNameField.getText().trim(), lastNameField.getText().trim(),
                genderComboBox.getValue(), Integer.parseInt(carIdComboBox.getValue()),
                brandField.getText().trim(), modelField.getText().trim(),
                finalTotalCost, dateRentedPicker.getValue(), dateReturnPicker.getValue()
        );

        if (ok) {
            showAlert("Success", "Car rented successfully! Booking saved.");
            clearRentForm();
            refreshCarList();
        } else {
            showAlert("Error", "Could not complete rental booking.");
        }
    }

    // shob field faka korar method
    @FXML
    public void clearRentForm() {
        carIdComboBox.setValue(null);
        brandField.setText(""); modelField.setText("");
        firstNameField.setText(""); lastNameField.setText("");
        genderComboBox.setValue(null);
        dateRentedPicker.setValue(null); dateReturnPicker.setValue(null);
        rentalDaysLabel.setText("0 Days"); totalCostLabel.setText("$0.00");
        amountPaidField.setText(""); balanceLabel.setText("$0.00");
        dailyPrice = 0.0; finalTotalCost = 0.0;
    }

    // alert message dekhate ei method use kora hoy
    private void showAlert(String title, String msg) {
        Alert a = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
