package com.codecafe.car_rental;

import java.sql.Date;

// car er data dhorar model class
public class CarData {

    private Integer carId;
    private String brand;
    private String model;
    private Double price;
    private String status;
    private Date date;
    private String image;

    // constructor
    public CarData(Integer carId, String brand, String model, Double price, String status, String image, Date date) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.status = status;
        this.date = date;
        this.image = image;
    }

    // getter method gulo
    public Integer getCarId() { return carId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public Double getPrice() { return price; }
    public String getStatus() { return status; }
    public Date getDate() { return date; }
    public String getImage() { return image; }
}
