package com.gamecastle.Models;

public class User implements UserActions {

    private String username;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean authenticate(String username, String password) {

        return (this.username.equals(username) && this.password.equals(password));

    }

}
