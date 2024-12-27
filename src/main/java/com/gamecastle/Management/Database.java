package com.gamecastle.Management;

import com.gamecastle.Models.Admin;
import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;
import com.gamecastle.Models.User;

import java.util.ArrayList;

import static com.gamecastle.Management.FileManager.loadCustomers;

public class Database implements DatabaseManagement {

    private ArrayList<Customer> customers;
    private ArrayList<Game> games;
    private Customer loggedInCustomer;

    public Database() {
        customers = loadCustomers();
        games = FileManager.loadGames();
        if (this.customers == null) {
            this.customers = new ArrayList<>();
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomers();
    }

    @Override
    public void addGame(Game game) {
        games.add(game);
        saveGames();
    }

    @Override
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

    public boolean isAdmin(User user) {

        Admin admin = new Admin();
        return (admin.getUsername().equals(user.getUsername()) && admin.getPassword().equals(user.getPassword()));

    }

    public Customer validateCustomer(User user) {
        for (Customer customer : customers) {
            if (customer.authenticate(user.getUsername(), user.getPassword())) {
                this.loggedInCustomer = customer;
                return customer;
            }
        }
        return null;
    }

    @Override
    public void saveCustomers() {
        FileManager.saveCustomers(customers);
    }

    @Override
    public void saveGames() {
        FileManager.saveGames(games);
    }

    public Customer getLoggedInCustomer() {
        return this.loggedInCustomer;
    }

    @Override
    public void saveUser(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUsername().equals(customer.getUsername())) {
                customers.set(i, customer);
                saveCustomers();
                return;
            }
        }
    }

    @Override
    public void deleteUser(Customer customer) {
        customers.remove(customer);
        saveCustomers();
    }

}
