package com.gamecastle.Models;

public class Game extends Purchasable implements Comparable<Game> {

    public Game() { }

    public Game(String name, double price)
    {
        super(name, price);
    }

    @Override
    public int compareTo(Game o) {

        if (!(this.getName().equals(o.getName()))) {
            return this.getName().compareTo(o.getName());
        }
        else
        {
            return (int)(this.getPrice() - o.getPrice());
        }
    }

    @Override
    public String toString() {
        return this.getName() + " | $" + String.format("%.2f", this.getPrice());
    }
}
