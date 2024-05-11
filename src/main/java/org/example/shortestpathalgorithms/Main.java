package org.example.shortestpathalgorithms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import org.example.shortestpathalgorithms.Singeltons.AudioPlayer;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Designs/start-window.fxml"));
        Image icon = new Image(String.valueOf(Main.class.getResource("Pictures/icon.jpg")));
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shortest Path Algorithms");
        stage.setScene(scene);
        stage.setResizable(false);

        MediaPlayer mediaPlayer = AudioPlayer.getInstance();
        mediaPlayer.play();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}