package com.gamecastle.Models;

import java.util.ArrayList;

public class Customer extends User implements LibraryManagement {

    private String phone;
    private ArrayList<Game> library;
    private double wallet;

    public Customer() {
        this.library = new ArrayList<>();
        this.wallet = 0.00;
    }

    public Customer(String username, String password, String phone) {
        super(username, password);
        this.phone = phone;
        this.library = new ArrayList<>();
        this.wallet = 0.00;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getWallet() {
        return wallet;
    }

    public void updateWallet(double wallet) {
        this.wallet += wallet;
    }

    @Override
    public ArrayList<Game> getLibrary() {
        return this.library;
    }

    @Override
    public void addGame(Game game) {
        this.library.add(game);
    }

    @Override
    public void deleteGame(Game game) {
        this.library.remove(game);
    }
}
