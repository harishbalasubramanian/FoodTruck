package com.example.foodtruck;

public class FoodTruck {
    private String name;
    private String address;
    private String type;
    private double rating;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String hours;

    public FoodTruck(String name, String address, String type, double rating, double latitude, double longitude, String phoneNumber, String hours) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public double getRating() {
        return rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getHours() {
        return hours;
    }
}

