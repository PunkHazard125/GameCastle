package com.gamecastle.Management;

import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;

public interface DatabaseManagement {

    public void addCustomer(Customer customer);

    public void addGame(Game game);

    public void removeGame(String name);

    public void saveCustomers();

    public void saveGames();

    public void saveUser(Customer customer);

    public void deleteUser(Customer customer);

}
