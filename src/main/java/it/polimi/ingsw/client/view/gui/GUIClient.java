package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
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
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class GUIClient extends Application implements UserInterface {

    private static Stage stage;
    private MediaPlayer mediaPlayer;
    private final HashMap<ClientStatusEnum, URL> resourcesMap;
    private final HashMap<ClientStatusEnum, SceneController> controllersMap;
    private final LinkedBlockingQueue<String> notificationsQueue;

    public GUIClient() {
        resourcesMap = new HashMap<>();
        controllersMap = new HashMap<>();
        notificationsQueue = new LinkedBlockingQueue<>();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> Platform.runLater(() -> {
            try {
                if (!notificationsQueue.isEmpty())
                    Notifications.create().darkStyle().title("").text(notificationsQueue.take()).owner(stage.getOwner()).position(Pos.BOTTOM_RIGHT).show();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }), 3000, 500, TimeUnit.MILLISECONDS);
        loadScenes();
    }

    @Override
    public void runView() {
        launch();
    }

    @Override
    public void showMessage(String message) {
        message = message.replace("\u001B[0m", "");
        String finalMessage = message.startsWith("\u001B") ? message.substring(5) : message;
        try {
            notificationsQueue.put(finalMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) {
        GUIClient.stage = stage;

        stage.setTitle("Codex Naturalis");
        stage.setMinHeight(800);
        stage.setMinWidth(1300);
        stage.setHeight(800);
        stage.setWidth(1300);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        changeScene(ClientStatusEnum.LOBBY);
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
                    new KeyFrame(Duration.seconds(5), new KeyValue(mediaPlayer.volumeProperty(), 0.1))
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
        resourcesMap.put(ClientStatusEnum.CHOOSING_COLOR, getClass().getResource("/fxml/ChooseColor.fxml"));
        resourcesMap.put(ClientStatusEnum.CHOOSING_STARTER_FACE, getClass().getResource("/fxml/ChooseStarterFace.fxml"));
        resourcesMap.put(ClientStatusEnum.CHOOSING_OBJECTIVE, getClass().getResource("/fxml/ChooseObjective.fxml"));
        resourcesMap.put(ClientStatusEnum.PLAYING, getClass().getResource("/fxml/Game.fxml"));
        resourcesMap.put(ClientStatusEnum.LAST_TURN, getClass().getResource("/fxml/Game.fxml"));
        resourcesMap.put(ClientStatusEnum.ENDED, getClass().getResource("/fxml/EndGame.fxml"));
    }

    public void changeScene(ClientStatusEnum status) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(resourcesMap.get(status));
                stage.setScene(new Scene(loader.load()));
                controllersMap.put(status, loader.getController());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateCurrentScene(String data) {
        SceneController controller = controllersMap.get(ClientManager.getInstance().getGameState().getClientStatus());
        if (controller == null) return;
        Platform.runLater(() -> controller.updateScene(data));
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