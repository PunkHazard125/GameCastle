package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CustomerPanelController {

    @FXML
    private Pane walletPane;
    @FXML
    private Label balanceLabel;
    @FXML
    private TextField amountField;
    @FXML
    private Button update;
    @FXML
    private Button goBackWalletMenu;

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

        walletPane.setVisible(false);

        goBackWalletMenu.setOnAction(event -> walletPane.setVisible(false));

        update.setOnAction(event -> {
            try {
                String amountText = amountField.getText().trim();
                double amount = Double.parseDouble(amountText);

                if (amount <= 0) {
                    showAlert("Invalid Amount", "Please enter a positive amount.");
                    return;
                }

                Customer loggedInCustomer = database.getLoggedInCustomer();
                if (loggedInCustomer != null) {
                    loggedInCustomer.updateWallet(amount);
                    database.saveUser(database.getLoggedInCustomer());
                    showInfo("Wallet Updated", "Your wallet has been updated successfully!");
                    amountField.clear();
                    walletPane.setVisible(false);
                } else {
                    showAlert("Error", "No customer is currently logged in.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a numeric value.");
            }
        });

    }

    @FXML
    public void handleLibrary() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/Library.fxml"));
            Parent root = loader.load();

            LibraryController controller = loader.getController();
            controller.setDatabase(database);
            controller.setLibraryView();

            Stage libraryStage = new Stage();
            controller.setStages(libraryStage, this.stage);
            libraryStage.setTitle("Library");
            libraryStage.setScene(new Scene(root));
            libraryStage.show();

            this.stage.hide();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void handlePurchase() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/Store.fxml"));
            Parent root = loader.load();

            StoreController controller = loader.getController();
            controller.setDatabase(database);
            Stage storeStage = new Stage();
            controller.setStages(storeStage, stage);

            storeStage.setTitle("Store");
            storeStage.setScene(new Scene(root));
            storeStage.show();

            this.stage.hide();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        try {
            Customer loggedInCustomer = database.getLoggedInCustomer();

            if (loggedInCustomer != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Delete Account");
                confirmationAlert.setHeaderText("Are you sure you want to delete your account?");
                confirmationAlert.setContentText("This action is irreversible.");
                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        database.deleteUser(loggedInCustomer);

                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                        infoAlert.setTitle("Account Deleted");
                        infoAlert.setContentText("Your account has been successfully deleted.");
                        infoAlert.showAndWait();

                        stage.close();
                        loginStage.show();
                    }
                });
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("No customer is currently logged in.");
                errorAlert.showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Unable to delete the account. Please try again.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void handleWallet() {

        walletPane.setVisible(true);
        balanceLabel.setText(String.format("$%.2f", database.getLoggedInCustomer().getWallet()));

    }

    @FXML
    public void handleGoBack() {

        stage.close();
        loginStage.show();

    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
