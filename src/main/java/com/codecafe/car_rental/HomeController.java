package com.codecafe.car_rental;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

// home dashboard er count ar chart er controller
public class HomeController implements Initializable {

    @FXML private Label availableCarsLabel, totalIncomeLabel, totalCustomersLabel;
    @FXML private BarChart<?, ?> incomeBarChart;
    @FXML private LineChart<?, ?> customerLineChart;

    // home page ashar shathe shathe eita run hoy
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadHomeCardsData();
        loadCharts();
    }

    // card gulate count ar total taka show korar method
    private void loadHomeCardsData() {
        try (Connection con = Database.connectDb()) {
            // koyta car available ache tar count
            ResultSet rs1 = con.prepareStatement("SELECT COUNT(id) FROM codecafe_cars WHERE status = 'Available'").executeQuery();
            if (rs1.next()) availableCarsLabel.setText(String.valueOf(rs1.getInt(1)));

            // total income er jogfol
            ResultSet rs2 = con.prepareStatement("SELECT SUM(total) FROM rental_bookings").executeQuery();
            if (rs2.next()) totalIncomeLabel.setText("$" + String.format("%.2f", rs2.getDouble(1)));

            // total customer er sonkha
            ResultSet rs3 = con.prepareStatement("SELECT COUNT(id) FROM rental_bookings").executeQuery();
            if (rs3.next()) totalCustomersLabel.setText(String.valueOf(rs3.getInt(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bar chart ar line chart load korar method
    private void loadCharts() {
        incomeBarChart.getData().clear();
        customerLineChart.getData().clear();
        try (Connection con = Database.connectDb()) {
            // daily income er bar chart
            ResultSet rsIncome = con.prepareStatement("SELECT date_rented, SUM(total) FROM rental_bookings GROUP BY date_rented ORDER BY date_rented ASC LIMIT 6").executeQuery();
            XYChart.Series seriesIncome = new XYChart.Series();
            while (rsIncome.next()) seriesIncome.getData().add(new XYChart.Data(rsIncome.getString(1), rsIncome.getDouble(2)));
            incomeBarChart.getData().add(seriesIncome);

            // customer count er line chart
            ResultSet rsCust = con.prepareStatement("SELECT date_rented, COUNT(id) FROM rental_bookings GROUP BY date_rented ORDER BY date_rented ASC LIMIT 5").executeQuery();
            XYChart.Series seriesCust = new XYChart.Series();
            while (rsCust.next()) seriesCust.getData().add(new XYChart.Data(rsCust.getString(1), rsCust.getInt(2)));
            customerLineChart.getData().add(seriesCust);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
