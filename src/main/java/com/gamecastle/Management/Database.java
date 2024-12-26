package com.gamecastle.Management;

import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;

import java.util.ArrayList;

public class Database {

    private ArrayList<Customer> customers;
    private ArrayList<Game> games;
    private Customer loggedInCustomer;

    public Database() {
        customers = FileManager.loadCustomers();
        games = FileManager.loadGames();
        if (this.customers == null) {
            this.customers = new ArrayList<>();
        }
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomers();
    }

    public void addGame(Game game) {
        games.add(game);
        saveGames();
    }

    public void removeGame(String name) {
        for (Game game : games){
            if (game.getName().equals(name)) {
                games.remove(game);
                break;
            }
        }
        saveGames();
    }

    public boolean checkAccountExistence(String username){
        boolean exists = false;
        for (Customer customer : customers){
            if (customer.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean checkGameExistence(String name){
        boolean exists = false;
        for (Game game : games){
            if (game.getName().equals(name)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public Customer Validate(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                this.loggedInCustomer = customer;
                return customer;
            }
        }
        return null;
    }

    private void saveCustomers() {
        FileManager.saveCustomers(customers);
    }

    private void saveGames() {
        FileManager.saveGames(games);
    }

    public Customer getLoggedInCustomer() {
        return this.loggedInCustomer;
    }

    public void saveUser(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername().equals(customer.getUsername())) {
                customers.set(i, customer);
                saveCustomers();
                return;
            }
        }
    }

    public void deleteUser(Customer customer) {
        customers.remove(customer);
        saveCustomers();
    }

}
