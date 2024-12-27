package com.gamecastle.Controllers;

import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;

import java.util.ArrayList;
import java.util.Random;

public interface GameServices {

    static String generateOTP() {

        Random random = new Random();
        int code = random.nextInt(90001) + 510000;
        return Integer.toString(code);

    }

    static boolean verifyOTP(String otp1, String otp2) {

        return otp1.equals(otp2);

    }

    void purchaseGame();
    void addGamesToLibrary(ArrayList<Game> cart, Customer customer);
    double calculateTotal(ArrayList<Game> games);

}
