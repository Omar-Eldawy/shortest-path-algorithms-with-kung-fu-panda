package org.example.shortestpathalgorithms.Singeltons;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.shortestpathalgorithms.Main;

public class AudioPlayer {
    private static MediaPlayer mediaPlayer;

    private AudioPlayer() {
    }

    public static MediaPlayer getInstance() {
        if (mediaPlayer == null) {
            String path = String.valueOf(Main.class.getResource("Music/HowToTrainYourDragon.mp3"));
            Media sound = new Media(path);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return mediaPlayer;
    }
}
