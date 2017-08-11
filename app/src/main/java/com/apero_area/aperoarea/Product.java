package com.apero_area.aperoarea;

/**
 * Created by micka on 10-Aug-17.
 */

public class Product {

    private int id;
    private String name;
    private String description;
    private String price;

    //getters & setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

