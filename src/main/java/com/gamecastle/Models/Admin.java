package com.gamecastle.Models;

public class Admin extends User {

    public Admin() {
        super("Admin", "212325");
    }

    @Override
    public void setUsername(String username) {
        System.out.println("Username cannot be changed for Admin");
    }

    @Override
    public void setPassword(String password) {
        System.out.println("Password cannot be changed for Admin");
    }

}
