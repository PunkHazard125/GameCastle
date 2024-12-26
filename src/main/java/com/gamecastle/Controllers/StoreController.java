package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Management.FileManager;
import com.gamecastle.Models.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StoreController {

    private Stage customerStage;
    private Stage stage;
    private Database database;
    private ArrayList<Game> library;

    public void setDatabase(Database database) {
        this.database = database;
        library = database.getLoggedInCustomer().getLibrary();
    }

    public void setStages(Stage stage, Stage customerStage) {
        this.stage = stage;
        this.customerStage = customerStage;
        Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
        stage.getIcons().add(icon);
    }

    @FXML
    private ListView<Game> gameListView;
    @FXML
    private ListView<Game> cartListView;

    private ObservableList<Game> gameList;
    private ObservableList<Game> cart;

    @FXML
    public void initialize() {

        gameList = FXCollections.observableArrayList(FileManager.loadGames());
        cart = FXCollections.observableArrayList();

        FXCollections.sort(gameList);

        gameListView.setItems(gameList);
        cartListView.setItems(cart);

        gameListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        cartListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void handleAddToCart() {

        Game selectedGame = gameListView.getSelectionModel().getSelectedItem();

        if (selectedGame == null) {
            showError("No Selection", "Please select a game to add to the cart.");
            return;
        }

        if (checkDuplicate(selectedGame)) {
            showError("Duplicate", "This game is already in your library!");
            return;
        }

        if (cart.contains(selectedGame)) {
            gameListView.getSelectionModel().clearSelection();
            showError("Duplicate", "This game is already in your cart.");
            return;
        }

        cart.add(selectedGame);
        gameListView.getSelectionModel().clearSelection();
        showInfo("Success", "Game added to the cart!");
    }

    @FXML
    private void handleRemoveFromCart() {

        Game selectedGame = cartListView.getSelectionModel().getSelectedItem();

        if (selectedGame == null) {
            showError("No Selection", "Please select a game to remove from the cart.");
            return;
        }

        cart.remove(selectedGame);
        cartListView.getSelectionModel().clearSelection();
        showInfo("Success", "Game removed from the cart!");

    }

    @FXML
    private void handlePurchase() {

        if (cart.isEmpty())
        {
            showError("Empty Cart", "You have to add at least one game to your cart!");
            return;
        }

        for (Game game : cart) {
            if (checkDuplicate(game)) {
                showError("Duplicate", "Duplicate game from library found in cart!");
                return;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/PurchasePanel.fxml"));
            Parent root = loader.load();

            PurchasePanelController controller = loader.getController();
            controller.setDatabase(database);

            Stage purchaseStage = new Stage();
            controller.setStages(purchaseStage, this.stage, new ArrayList<>(cart));
            purchaseStage.setTitle("Purchase Panel");
            purchaseStage.setScene(new Scene(root));
            purchaseStage.show();

            this.stage.hide();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean checkDuplicate(Game game) {

        for (Game temp : library)
        {
            if (temp.getName().equals(game.getName()))
            {
                return true;
            }
        }

        return false;

    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void goBack() {

        stage.close();
        customerStage.show();
    }

}
