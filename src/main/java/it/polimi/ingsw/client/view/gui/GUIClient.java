package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.gui.controllers.SceneController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class GUIClient extends Application implements UserInterface {

    private static Stage stage;
    private MediaPlayer mediaPlayer;
    private final HashMap<ClientStatusEnum, FXMLLoader> loadersMap;
    private final HashMap<ClientStatusEnum, SceneController> controllersMap;

    public GUIClient() {
        loadersMap = new HashMap<>();
        controllersMap = new HashMap<>();
        loadScenes();
    }

    @Override
    public void runView() {
        launch();
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void start(Stage stage) {
        GUIClient.stage = stage;
        updateScene(ClientStatusEnum.LOBBY);
        stage.setTitle("Codex Naturalis");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        stage.requestFocus();
        stage.show();

        stage.setOnCloseRequest(e -> System.exit(0));

        Media music = new Media(Objects.requireNonNull(getClass().getResource("/music/preGameMusic.mp3")).toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0);
        mediaPlayer.play();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.seconds(5), new KeyValue(mediaPlayer.volumeProperty(), 0.15))
        );
        fadeIn.play();
    }

    public void loadScenes() {
        FXMLLoader login = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        loadersMap.put(ClientStatusEnum.LOBBY, login);

        FXMLLoader createGame = new FXMLLoader(getClass().getResource("/fxml/CreateGame.fxml"));
        loadersMap.put(ClientStatusEnum.LOGGED, createGame);

        FXMLLoader gameLobby = new FXMLLoader(getClass().getResource("/fxml/GameLobby.fxml"));
        loadersMap.put(ClientStatusEnum.CONNECTED, gameLobby);
    }

    public void updateScene(ClientStatusEnum status) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = loadersMap.get(status);
                Scene scene = new Scene(loader.load());
                controllersMap.put(status, loader.getController());
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Stage getStage() {
        return stage;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public HashMap<ClientStatusEnum, FXMLLoader> getLoadersMap() {
        return loadersMap;
    }

    public HashMap<ClientStatusEnum, SceneController> getControllersMap() {
        return controllersMap;
    }
}