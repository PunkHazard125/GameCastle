package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PurchasePanelController implements GameServices {

    @FXML
    private Label total;
    @FXML
    private TextField codeField;
    @FXML
    private Pane verificationPane;
    @FXML
    private Pane codePane;
    @FXML
    private Label otpLabel;
    @FXML
    private Button generateCode;
    @FXML
    private Button submitCode;
    @FXML
    private Button copyCode;
    @FXML
    private Pane finalPane;
    @FXML
    private Label finalAmountLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Button ok;
    @FXML
    private Pane progressPane;
    @FXML
    private ProgressBar progressBar;

    private Database database;
    private Stage stage;
    private Stage storeStage;
    private ArrayList<Game> cart;
    private double totalAmount;
    private boolean verification;
    private String otp;
    private String purchaseTime;

    public void setDatabase(Database database) {
        this.database = database;
        this.verification = false;
        this.totalAmount = 0.00;
    }

    public void setStages(Stage stage, Stage storeStage, ArrayList<Game> cart) {
        this.stage = stage;
        this.storeStage = storeStage;
        this.cart = cart;
        Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
        stage.getIcons().add(icon);

        this.totalAmount = calculateTotal(this.cart);

        this.total.setText(String.format("$%.2f", totalAmount));
    }

    @FXML
    private void initialize() {

        codePane.setVisible(false);
        verificationPane.setVisible(false);
        finalPane.setVisible(false);

        generateCode.setOnAction ( event -> {

            codePane.setVisible(true);

            this.otp = GameServices.generateOTP();
            otpLabel.setText(this.otp);

        });

        submitCode.setOnAction ( event -> {

            codePane.setVisible(false);

            if (this.otp == null)
            {
                showError("Invalid Choice", "Generate an OTP First!");
            }
            else if (GameServices.verifyOTP(this.otp, codeField.getText()))
            {
                verification = true;
                verificationPane.setVisible(true);
            }
            else
            {
                showError("Wrong OTP", "You have entered a wrong code!\nGenerate OTP Again");
            }

        });

        copyCode.setOnAction ( event -> {

            String textToCopy = this.otp;

            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(textToCopy);
            clipboard.setContent(content);

            showInfo("Copied", "Code Copied to Clipboard");

        });

    }

    @FXML
    private void handleGoBack() {

        this.stage.close();
        this.storeStage.show();

    }

    @FXML
    private void handleCancelPayment() {

        this.stage.close();
        this.storeStage.show();

    }

    @FXML
    private void handleConfirm() {

        this.purchaseTime = getRealTime();
        double balance = database.getLoggedInCustomer().getWallet();

        if (balance >= totalAmount && verification)
        {
            purchaseGame();

            progressPane.setVisible(true);
            finalPane.setVisible(false);
            startProgressBarAnimation();

        }
        else
        {
            if (!verification)
            {
                showError("Verification Incomplete", "Complete Verification First!");
                return;
            }
            showError("Insufficient Funds", "Oops! Looks like you forgot to refill your tank!\nMake sure to deposit enough funds.");
            handleGoBack();
        }

    }

    @FXML
    private void handleFinalStatus() {

        finalPane.setVisible(true);

        finalAmountLabel.setText(String.format("$%.2f", totalAmount));
        timeLabel.setText(this.purchaseTime);

        ok.setOnAction(event -> {

            this.stage.close();

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/LoginPanel.fxml"));
                Parent root = loader.load();

                LoginPanelController controller = loader.getController();
                controller.setDatabase(database);

                Stage loginStage = new Stage();
                controller.setPrimaryStage(loginStage);
                Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
                loginStage.getIcons().add(icon);
                loginStage.setTitle("Login Panel");
                loginStage.setScene(new Scene(root));
                loginStage.show();
                loginStage.setResizable(false);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }

    private String getRealTime() {

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
        return currentTime.format(formatter);

    }

    @FXML
    private void startProgressBarAnimation() {
        finalPane.setVisible(true);
        progressPane.setVisible(true);
        progressBar.setProgress(0);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(3), new KeyValue(progressBar.progressProperty(), 1))
        );

        timeline.setOnFinished(event -> {
            progressPane.setVisible(false);
            handleFinalStatus();
        });

        timeline.play();
    }

    @Override
    public void purchaseGame() {

        database.getLoggedInCustomer().updateWallet(-totalAmount);

        addGamesToLibrary(cart, database.getLoggedInCustomer());

        database.saveUser(database.getLoggedInCustomer());

    }

    @Override
    public void addGamesToLibrary(ArrayList<Game> cart, Customer customer) {

        for (Game game : cart)
        {
            customer.addGame(game);
        }

    }

    @Override
    public double calculateTotal(ArrayList<Game> cart) {

        double total = 0;

        for (Game game : cart)
        {
            total += game.getPrice();
        }

        return total;

    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
