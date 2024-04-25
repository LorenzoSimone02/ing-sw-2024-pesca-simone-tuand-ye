package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Codex Naturalis");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        stage.setScene(scene);
        stage.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nextLine;
        ClientNetworkHandler networkHandler = null;
        ViewModeEnum viewMode = ViewModeEnum.TUI;

        try {
            do {
                System.out.println("Please choose between CLI technology and GUI technology:");
                System.out.println("[1] TUI - [2] GUI");
                nextLine = scanner.nextLine().trim();

                if (nextLine.equals("1") || nextLine.equals("TUI")) {
                    System.out.println("You have selected TUI technology.");
                } else if (nextLine.equals("2") || nextLine.equals("GUI")) {
                    System.out.println("You have selected GUI technology.");
                    viewMode = ViewModeEnum.GUI;
                } else {
                    System.out.println("\"" + nextLine + "\" is not a valid option. Please try again.");
                }

            } while (!(nextLine.equals("1") || nextLine.equals("2") || nextLine.equalsIgnoreCase("TUI") || nextLine.equalsIgnoreCase("GUI")));

            do {
                System.out.println("Now please choose between Socket technology and RMI technology:");
                System.out.println("[1] Socket - [2] RMI");
                nextLine = scanner.nextLine().trim();

                if (nextLine.equals("1") || nextLine.equals("Socket")) {
                    System.out.println("You have selected Socket technology.");
                    networkHandler = new SocketClient("localhost", 5000);
                    System.out.println("Connected to the Lobby.");
                } else if (nextLine.equals("2") || nextLine.equals("RMI")) {
                    System.out.println("You have selected RMI technology.");
                    try {
                        networkHandler = new RMIClient("Server", 1099);
                        System.out.println("Connected to the Lobby");
                    } catch (IOException e) {
                        System.err.println("An error occured!");
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.out.println("\"" + nextLine + "\" is not a valid option. Please try again.");
                }

            } while (!(nextLine.equals("1") || nextLine.equals("2") || nextLine.equalsIgnoreCase("Socket") || nextLine.equalsIgnoreCase("RMI")));
        } catch (Exception e) {
            System.err.println("An error occured!");
            System.err.println(e.getMessage());
        }

        ClientManager clientManager = new ClientManager(networkHandler, viewMode);
        if (viewMode == ViewModeEnum.GUI) {
            launch();
        }
        clientManager.getGameState().setClientStatus(ClientStatusEnum.LOBBY);
        clientManager.getNetworkHandler().sendPacket(new JoinPacket());
        clientManager.runUI();
    }
}