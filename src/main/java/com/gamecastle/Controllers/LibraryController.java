package com.gamecastle.Controllers;

import com.gamecastle.Management.Database;
import com.gamecastle.Models.Customer;
import com.gamecastle.Models.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LibraryController {

    private Database database;
    private Customer loggedInCustomer;
    private Stage stage;
    private Stage customerStage;

    public void setDatabase(Database database) {
        this.database = database;
        this.loggedInCustomer = database.getLoggedInCustomer();
    }

    public void setStages(Stage stage, Stage customerStage) {
        this.stage = stage;
        this.customerStage = customerStage;
        Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
        stage.getIcons().add(icon);
    }

    @FXML
    private ListView<Game> libraryView;

    @FXML
    public void initialize() {

        libraryView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        libraryView.setItems(null);
    }

    @FXML
    public void setLibraryView() {
        ObservableList<Game> library = FXCollections.observableArrayList(database.getLoggedInCustomer().getLibrary());
        FXCollections.sort(library);
        libraryView.setItems(library);
    }

    @FXML
    public void handleDeleteGame() {

        Game selectedGame = libraryView.getSelectionModel().getSelectedItem();

        if (selectedGame != null) {
            loggedInCustomer.deleteGame(selectedGame);

            libraryView.setItems(FXCollections.observableArrayList(loggedInCustomer.getLibrary()));

            database.saveUser(loggedInCustomer);

            showAlert("Game Deleted", "The selected game has been removed from your library.");
        }
        else
        {
            showAlert("No Game Selected", "Please select a game to delete.");
        }
    }

    @FXML
    public void handleGoBack() {
        this.stage.close();
        customerStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
