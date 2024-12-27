package com.gamecastle.Models;

public abstract class Purchasable {

    private String name;
    private double price;

    public Purchasable() {}

    public Purchasable(String name, double price) {

        this.name = name;
        this.price = price;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public abstract String toString();

}
