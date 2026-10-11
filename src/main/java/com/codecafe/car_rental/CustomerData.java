package com.codecafe.car_rental;

import java.sql.Date;

// customer booking history dhorar model class
public class CustomerData {

    private Integer customerId;
    private String fullName;
    private String carDetails;
    private Double total;
    private Date dateRented;
    private Date dateReturn;

    // constructor
    public CustomerData(Integer customerId, String fullName, String carDetails, Double total, Date dateRented, Date dateReturn) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.carDetails = carDetails;
        this.total = total;
        this.dateRented = dateRented;
        this.dateReturn = dateReturn;
    }

    // getter method gulo
    public Integer getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getCarDetails() { return carDetails; }
    public Double getTotal() { return total; }
    public Date getDateRented() { return dateRented; }
    public Date getDateReturn() { return dateReturn; }
}
