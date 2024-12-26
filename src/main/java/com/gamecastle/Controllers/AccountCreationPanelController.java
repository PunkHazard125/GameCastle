package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AccountCreationPanelController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ToggleButton showPassword;
    @FXML
    private TextField phoneNumberField;

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
    public void initialize() {

        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        showPassword.setOnAction(event -> {
            if (showPassword.isSelected()) {
                passwordField.setVisible(false);
                passwordTextField.setVisible(true);
                showPassword.setText("Hide");
            } else {
                passwordField.setVisible(true);
                passwordTextField.setVisible(false);
                showPassword.setText("Show");
            }
        });
    }

    @FXML
    private void handleCreateAccount() {

        String username = usernameField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();

        if(username.isEmpty()){
            showAlert("Please enter Username");
            return;
        }
        if(password.isEmpty()){
            showAlert("Please enter Password");
            return;
        }
        if(phoneNumber.isEmpty()){
            showAlert("Please enter phone number");
            return;
        }
        if(database.checkAccountExistence(username)){
            showAlert("Account already exists.");
            return;
        }

        database.addCustomer(new Customer(username, password, phoneNumber));

        stage.close();
        loginStage.show();
    }

    @FXML
    public void handleGoBack() {

        this.stage.close();
        this.loginStage.show();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

}
