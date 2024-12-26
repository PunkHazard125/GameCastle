package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Models.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminPanelController {

    @FXML
    private TextField addNameField;
    @FXML
    private TextField addPriceField;
    @FXML
    private TextField removeNameField;

    private Database database;

    private Stage stage;
    private Stage loginStage;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setStages(Stage stage, Stage loginStage) {
        this.stage = stage;
        this.loginStage = loginStage;
        Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
        stage.getIcons().add(icon);
    }

    @FXML
    private void addGame() {

        String name = addNameField.getText();
        String price_str = addPriceField.getText();
        double price = Double.parseDouble(price_str);

        if(name.isEmpty()){
            showAlert("Please enter a name");
            return;
        }
        if(price_str.isEmpty()){
            showAlert("Please enter a price");
            return;
        }

        if (database.checkGameExistence(name))
        {
            showAlert("Game already exists.");
            return;
        }

        database.addGame(new Game(name, price));

        showSuccessStatus("Game Added!");

    }

    @FXML
    private void removeGame() {

        String name = removeNameField.getText();

        if(name.isEmpty()){
            showAlert("Please enter a name");
            return;
        }

        if (!(database.checkGameExistence(name)))
        {
            showAlert("Game does not exist.");
            return;
        }

        database.removeGame(name);

        showSuccessStatus("Game Removed!");

    }

    @FXML
    private void goBack() {

        stage.close();
        loginStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showSuccessStatus(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

}
