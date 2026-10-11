package com.codecafe.car_rental;

import java.sql.Connection;
import java.sql.DriverManager;

// database connect korar helper class
public class Database {

    // mysql database er sathe connect korar method
    public static Connection connectDb() {
        try {
            // codecafe_rental_db database er url
            String url = "jdbc:mysql://localhost:3306/codecafe_rental_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "mdsakibur@610");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
