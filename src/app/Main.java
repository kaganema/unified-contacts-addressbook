package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * An application to create a contact list address book, with additional details for adding links online.
 * @author Emir Atik (github: kaganema) (using code based on Oracle's docs and others)
 * @version 1.0 27/07/2022
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Unified Contacts Manager");
        primaryStage.setScene(new Scene(root, 700, 530));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
