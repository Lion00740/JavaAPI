package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
public class APIController implements Initializable {
    private Media media;
    private MediaPlayer mediaPlayer;
    private File directoryMedia = new File("/media");
    public static int mediaNumber;
    private ClientController clientController = new ClientController();
    @FXML
    private MediaView mediaView;
    @FXML
    private Button playButton;
    @FXML
    private Slider volumeSlider, timecodesSlider;
    @FXML
    private HBox box;
    @FXML
    private Label label;
    @FXML
    private Button fullscreenButton;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        selectionFile();
    }
    @FXML
    protected void playMedia() {
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                timecodesSlider.setValue(t1.toSeconds());
            }
        });
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

        if (mediaPlayer.getStatus() == MediaPlayer.Status.READY || mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED)
        {
            mediaPlayer.play();
            playButton.setText("PAUSE");

        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playButton.setText("PLAY");
        }
    }
    @FXML
    protected void resetMedia() {
        mediaPlayer.seek(Duration.seconds(0.0));
        timecodesSlider.setValue(0);
    }
    @FXML
    protected void timecodesPressed() {
        mediaPlayer.seek(Duration.seconds(timecodesSlider.getValue()));
    }
    @FXML
    protected void timecodesDragged() {
        mediaPlayer.seek(Duration.seconds(timecodesSlider.getValue()));
    }
    @FXML
    protected void mouseEntered() {
        box.setVisible(true);
        timecodesSlider.setVisible(true);
        fullscreenButton.setVisible(true);
    }
    @FXML
    protected void mouseExited() {

        box.setVisible(false);
        timecodesSlider.setVisible(false);
        fullscreenButton.setVisible(false);
    }
    @FXML
    protected void nextMedia() {
        if (mediaNumber < 6) {
            mediaNumber++;

            selectionFile();
            playMedia();
        }
    }
    @FXML
    protected void prevMedia() {
        if (mediaNumber > 0) {
            mediaNumber--;

            selectionFile();
            playMedia();
        }
    }
    @FXML
    protected void fullscreen() {
        if (MainWindow.myStage.isFullScreen()) {
            MainWindow.myStage.setFullScreen(false);
        } else {
            MainWindow.myStage.setFullScreen(true);
        }
    }
    protected void selectionFile() {
        clientController.sendPacket();
        String nameFile = clientController.receiveNameFile();
        File temporary = clientController.receiveFile(nameFile);

        String[] strWithoutunderline = nameFile.split(".mp4");

        label.setText(strWithoutunderline[0]);

        media = new Media(temporary.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }
}