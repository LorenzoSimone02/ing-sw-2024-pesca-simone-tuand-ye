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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class GUIClient extends Application implements UserInterface {

    private static Stage stage;
    private MediaPlayer mediaPlayer;
    private final HashMap<ClientStatusEnum, URL> resourcesMap;
    private final HashMap<ClientStatusEnum, SceneController> controllersMap;

    public GUIClient() {
        resourcesMap = new HashMap<>();
        controllersMap = new HashMap<>();
        loadScenes();
    }

    @Override
    public void runView() {
        launch();
    }

    @Override
    public void showMessage(String message) {
        message = message.replace("\u001B[0m", "");
        String finalMessage = message;
        Platform.runLater(() -> {
            Notifications.create().darkStyle().title("").text(finalMessage).position(Pos.BOTTOM_RIGHT).show();
        });
    }

    @Override
    public void start(Stage stage) {
        GUIClient.stage = stage;
        updateScene(ClientStatusEnum.LOBBY);
        stage.setTitle("Codex Naturalis");
        stage.setMinHeight(600);
        stage.setMinWidth(900);
        stage.setHeight(1080);
        stage.setWidth(1920);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        stage.show();
        stage.requestFocus();

        stage.setOnCloseRequest(e -> System.exit(0));

        try {
            Media music = new Media(Objects.requireNonNull(getClass().getResource("/music/preGameMusic.mp3")).toURI().toString());
            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0);
            mediaPlayer.play();
            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.seconds(5), new KeyValue(mediaPlayer.volumeProperty(), 0.15))
            );
            fadeIn.play();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadScenes() {
        resourcesMap.put(ClientStatusEnum.LOBBY, getClass().getResource("/fxml/Login.fxml"));
        resourcesMap.put(ClientStatusEnum.LOGGED, getClass().getResource("/fxml/CreateGame.fxml"));
        resourcesMap.put(ClientStatusEnum.CONNECTED, getClass().getResource("/fxml/GameLobby.fxml"));
    }

    public void updateScene(ClientStatusEnum status) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(resourcesMap.get(status));
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

    public HashMap<ClientStatusEnum, URL> getResourcesMap() {
        return resourcesMap;
    }

    public HashMap<ClientStatusEnum, SceneController> getControllersMap() {
        return controllersMap;
    }
}