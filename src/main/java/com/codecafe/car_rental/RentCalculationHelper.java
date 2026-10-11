package com.codecafe.car_rental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// rent ar takar hisab nikesh er helper class
public class RentCalculationHelper {

    // koydin car use korbe tar hisab
    public static int calculateDays(LocalDate rentDate, LocalDate returnDate) {
        if (rentDate == null || returnDate == null) return 0;
        long days = ChronoUnit.DAYS.between(rentDate, returnDate);
        return (days <= 0) ? 1 : (int) days;
    }

    // daily rate die total taka hisab kora
    public static double calculateTotal(double ratePerDay, int days) {
        return ratePerDay * days;
    }

    // customer ke koto taka ferot dite hobe tar hisab
    public static double calculateBalance(double amountPaid, double totalCost) {
        return amountPaid - totalCost;
    }
}
