package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import logic.Controller;
import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class WotdApplication extends Application {
    public WotdApplication() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {

        Parent root = FXMLLoader.load(getClass().getResource("wotdView.fxml"));

        Scene scene = new Scene(root, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
