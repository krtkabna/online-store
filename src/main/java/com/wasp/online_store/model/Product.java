package com.wasp.online_store.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Product {
    private int id;
    private String name;
    private double price;
    private Date creationDate;

    public Product() {
    }

    public Product(int id, String name, double price, Date creationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDate = creationDate;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = roundPrice(price);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private double roundPrice(double price) {
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    @Override
    public String toString() {
        return "Product{" +
            "name='" + name + '\'' + ", price=" + price +
            '}';
    }
}
