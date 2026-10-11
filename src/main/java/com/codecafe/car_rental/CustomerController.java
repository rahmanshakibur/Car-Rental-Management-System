package com.codecafe.car_rental;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

// customer booking history page er controller
public class CustomerController implements Initializable {

    @FXML private TableView<CustomerData> customerTableView;
    @FXML private TableColumn<CustomerData, Integer> colCustomerId;
    @FXML private TableColumn<CustomerData, String> colFullName, colCarDetails;
    @FXML private TableColumn<CustomerData, Double> colTotal;
    @FXML private TableColumn<CustomerData, Date> colDateRented, colDateReturn;
    @FXML private TextField searchField;

    private ObservableList<CustomerData> bookingList;

    // page load hole table show hoy
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCustomerTable();
    }

    // table e customer list load korar method
    public void showCustomerTable() {
        bookingList = CustomerDatabaseHelper.getAllCustomerBookings();

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colCarDetails.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDateRented.setCellValueFactory(new PropertyValueFactory<>("dateRented"));
        colDateReturn.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));

        customerTableView.setItems(bookingList);
    }

    // customer name ba car model die search korar method
    @FXML
    public void searchCustomer() {
        FilteredList<CustomerData> filter = new FilteredList<>(bookingList, e -> true);

        searchField.textProperty().addListener((obs, oldV, newV) -> {
            filter.setPredicate(cust -> {
                if (newV == null || newV.isEmpty()) return true;
                String key = newV.toLowerCase().trim();
                return cust.getFullName().toLowerCase().contains(key)
                        || cust.getCarDetails().toLowerCase().contains(key)
                        || String.valueOf(cust.getCustomerId()).contains(key);
            });
        });

        SortedList<CustomerData> sorted = new SortedList<>(filter);
        sorted.comparatorProperty().bind(customerTableView.comparatorProperty());
        customerTableView.setItems(sorted);
    }

    // table refresh korar button method
    @FXML
    public void refreshTable() {
        showCustomerTable();
        searchField.setText("");
    }
}
