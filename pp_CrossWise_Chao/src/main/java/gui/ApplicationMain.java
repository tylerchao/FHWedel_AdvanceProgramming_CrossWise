package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


/**
 * Class that starts our application.
 *
 * @author chien-hsun
 */
public class ApplicationMain extends Application {

    /**
     * Creating the stage and showing it. This is where the initial size and the
     * title of the window are set.
     *
     * @param stage the stage to be shown
     * @throws IOException e
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("UserInterface.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1500, 800);
        stage.setTitle("CrossWise");
        stage.setMinWidth(1500);
        stage.setMinHeight(850);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Main method
     *
     * @param args unused
     */
    public static void main(String... args) {
        launch(args);
    }


}
