package com.gamecastle.Models;

public class Game implements Comparable<Game> {

    private String name;
    private double price;

    public Game() { }

    public Game(String name, double price)
    {
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

    @Override
    public int compareTo(Game o) {

        if (!(this.name.equals(o.name))) {
            return this.name.compareTo(o.name);
        }
        else
        {
            return (int)(this.price - o.price);
        }
    }

    @Override
    public String toString() {
        return getName() + " | $" + String.format("%.2f", getPrice());
    }
}
