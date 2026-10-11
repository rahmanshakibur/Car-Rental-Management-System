package com.codecafe.car_rental;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// car table er database kaj ekhane kora hoy
public class CarDatabaseHelper {

    // shob car database theke niye ashar method
    public static ObservableList<CarData> getAllCars() {
        ObservableList<CarData> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM codecafe_cars";

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

    // notun car add korar method
    public static boolean insertCar(int carId, String brand, String model, double price, String status, String image) {
        String sql = "INSERT INTO codecafe_cars (car_id, brand, model, price, status, image, date) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, carId);
            ps.setString(2, brand);
            ps.setString(3, model);
            ps.setDouble(4, price);
            ps.setString(5, status);
            ps.setString(6, image != null ? image : "");
            ps.setDate(7, new Date(System.currentTimeMillis()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // car update korar method
    public static boolean updateCar(int carId, String brand, String model, double price, String status, String image) {
        String sql = "UPDATE codecafe_cars SET brand=?, model=?, price=?, status=?, image=? WHERE car_id=?";
        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setDouble(3, price);
            ps.setString(4, status);
            ps.setString(5, image != null ? image : "");
            ps.setInt(6, carId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // car delete korar method
    public static boolean deleteCar(int carId) {
        String sql = "DELETE FROM codecafe_cars WHERE car_id=?";
        try (Connection con = Database.connectDb();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, carId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
