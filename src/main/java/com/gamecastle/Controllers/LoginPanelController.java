package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Models.Customer;
import com.gamecastle.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginPanelController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ToggleButton showPassword;
    @FXML
    private TextField passwordTextField;

    private Database database;
    private Stage primaryStage;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
        primaryStage.getIcons().add(icon);
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
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);

        if (database.isAdmin(user))
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/AdminPanel.fxml"));
                Parent root = loader.load();

                AdminPanelController controller = loader.getController();
                controller.setDatabase(database);
                Stage stage = new Stage();
                controller.setStages(stage, primaryStage);

                stage.setTitle("Admin Panel");
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);

                primaryStage.hide();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            Customer customer = database.validateCustomer(user);

            if (customer != null)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/CustomerPanel.fxml"));
                    Parent root = loader.load();

                    CustomerPanelController controller = loader.getController();
                    controller.setDatabase(database);
                    Stage stage = new Stage();
                    controller.setStages(stage, primaryStage);

                    stage.setTitle("Customer Panel");
                    stage.setScene(new Scene(root));
                    stage.show();
                    stage.setResizable(false);

                    primaryStage.hide();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials!", ButtonType.CLOSE);
                alert.showAndWait();
            }
        }

    }

    @FXML
    private void handleCreateAccount() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/AccountCreationPanel.fxml"));
            Parent root = loader.load();

            AccountCreationPanelController controller = loader.getController();
            controller.setDatabase(database);
            Stage stage = new Stage();
            controller.setStages(stage, primaryStage);

            stage.setTitle("Account Creation Panel");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);

            primaryStage.hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
