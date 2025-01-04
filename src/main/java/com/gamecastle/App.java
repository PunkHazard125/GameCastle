package com.gamecastle;

import com.gamecastle.Controllers.LoginPanelController;
import com.gamecastle.Management.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    private Database database;

    public App() {

        this.database = new Database();

    }

    @Override
    public void start(Stage primaryStage) {

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamecastle/LoginPanel.fxml"));
            Parent root = loader.load();

            LoginPanelController controller = loader.getController();
            controller.setDatabase(database);
            controller.setPrimaryStage(primaryStage);
            Image icon  = new Image(getClass().getResourceAsStream("/images/icon.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
