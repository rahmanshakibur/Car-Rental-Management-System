package com.codecafe.car_rental;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// rental er shob database kaj ekhane kora hoy
public class RentalDatabaseHelper {

    // available car gulo database theke load korar jonno
    public static ObservableList<CarData> getAvailableCars() {
        ObservableList<CarData> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM codecafe_cars WHERE status = 'Available'";

        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new CarData(
                        rs.getInt("car_id"), rs.getString("brand"),
                        rs.getString("model"), rs.getDouble("price"),
                        rs.getString("status"), rs.getString("image"),
                        rs.getDate("date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // car id die car details ber korar method
    public static CarData getCarById(int carId) {
        String sql = "SELECT * FROM codecafe_cars WHERE car_id = ?";
        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CarData(rs.getInt("car_id"), rs.getString("brand"),
                            rs.getString("model"), rs.getDouble("price"),
                            rs.getString("status"), rs.getString("image"), rs.getDate("date"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // booking confirm kore database e insert korar method
    public static boolean saveRentalBooking(String fName, String lName, String gender,
                                            int carId, String brand, String model,
                                            double total, LocalDate rentDate, LocalDate returnDate) {
        String insertSql = "INSERT INTO rental_bookings (customer_id, firstName, lastName, gender, car_id, brand, model, total, date_rented, date_return) VALUES (?,?,?,?,?,?,?,?,?,?)";
        String updateSql = "UPDATE codecafe_cars SET status = 'Not Available' WHERE car_id = ?";

        try (Connection con = Database.connectDb();
             PreparedStatement psInsert = con.prepareStatement(insertSql);
             PreparedStatement psUpdate = con.prepareStatement(updateSql)) {

            // notun customer id ber kora hocche
            int custId = 1;
            try (PreparedStatement check = con.prepareStatement("SELECT MAX(id) FROM rental_bookings");
                 ResultSet rs = check.executeQuery()) {
                if (rs.next()) custId = rs.getInt(1) + 1;
            }

            psInsert.setInt(1, custId);
            psInsert.setString(2, fName);
            psInsert.setString(3, lName);
            psInsert.setString(4, gender);
            psInsert.setInt(5, carId);
            psInsert.setString(6, brand);
            psInsert.setString(7, model);
            psInsert.setDouble(8, total);
            psInsert.setDate(9, Date.valueOf(rentDate));
            psInsert.setDate(10, Date.valueOf(returnDate));
            psInsert.executeUpdate();

            // car er status Not Available kora hocche
            psUpdate.setInt(1, carId);
            psUpdate.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
