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
 * @version 0.9 16/07/2022
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Unified Contacts Manager");
        primaryStage.setScene(new Scene(root, 700, 530));
        primaryStage.show();
    }

    /**
     * Initialise the user's directory
    * We want to return a value because we want to pass that same variable as reference.
     * @param name the url to the directory. */
    public File userDir(String name) throws Exception {
        File f = new File(name);
        f.mkdir();
        return f;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
