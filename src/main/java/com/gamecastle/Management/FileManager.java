package com.gamecastle.Management;

import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileManager {

    private static final String CUSTOMERS_FILE = "customers.json";
    private static final String GAMES_FILE = "games.json";
    private static final Gson gson = new Gson();

    private static <T> void saveToFile(String fileName, ArrayList<T> data) {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static <T> ArrayList<T> loadFromFile(String fileName, Type type) {
        try (Reader reader = new FileReader(fileName)) {
            ArrayList<T> data = gson.fromJson(reader, type);
            return (data != null) ? data : new ArrayList<>();
        }
        catch (NullPointerException ex) {
            return new ArrayList<>();
        }
        catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    public static void saveCustomers(ArrayList<Customer> customers) {
        saveToFile(CUSTOMERS_FILE, customers);
    }

    public static ArrayList<Customer> loadCustomers() {
        Type customerListType = new TypeToken<ArrayList<Customer>>() {}.getType();
        return loadFromFile(CUSTOMERS_FILE, customerListType);
    }

    public static void saveGames(ArrayList<Game> games) {
        saveToFile(GAMES_FILE, games);
    }

    public static ArrayList<Game> loadGames() {
        Type gameListType = new TypeToken<ArrayList<Game>>() {}.getType();
        return loadFromFile(GAMES_FILE, gameListType);
    }
}
