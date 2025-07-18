package Library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LibraryApp.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("University Library Loan Management");
            Image icon = new Image("ICON/Logo.png");
            primaryStage.getIcons().add(icon) ;

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
