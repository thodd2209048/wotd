package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WotdApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("wotdView.fxml"));
        primaryStage.setTitle("Hello world");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
