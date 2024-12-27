package com.gamecastle.Models;

import java.util.ArrayList;

public interface LibraryManagement {

    public ArrayList<Game> getLibrary();

    public void addGame(Game game);

    public void deleteGame(Game game);

}
