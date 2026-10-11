package com.codecafe.car_rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// customer history database er helper class
public class CustomerDatabaseHelper {

    // shob booking record database theke load korar method
    public static ObservableList<CustomerData> getAllCustomerBookings() {
        ObservableList<CustomerData> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM rental_bookings ORDER BY id DESC";

        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("firstName") + " " + rs.getString("lastName");
                String car = rs.getString("brand") + " " + rs.getString("model") + " (ID: " + rs.getInt("car_id") + ")";

                list.add(new CustomerData(
                        rs.getInt("customer_id"),
                        name,
                        car,
                        rs.getDouble("total"),
                        rs.getDate("date_rented"),
                        rs.getDate("date_return")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
