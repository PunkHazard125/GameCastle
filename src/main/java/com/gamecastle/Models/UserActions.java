package com.gamecastle.Models;

public interface UserActions {

    public boolean authenticate(String username, String password);
    public void setUsername(String username);
    public void setPassword(String password);

}

