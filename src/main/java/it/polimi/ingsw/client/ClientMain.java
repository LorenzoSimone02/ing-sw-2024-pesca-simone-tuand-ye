package it.polimi.ingsw.client;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.InfoPacket;
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
        String selected;
        ClientNetworkHandler client;

        try {
            do {
                System.out.println("Please choose between CLI technology and GUI technology:");
                System.out.println("[1] CLI - [2] GUI");
                selected = scanner.nextLine().trim();

                if (selected.equals("1") || selected.equals("CLI")) {
                    System.out.println("You have selected CLI technology.");
                } else if (selected.equals("2") || selected.equals("GUI")) {
                    System.out.println("You have selected GUI technology.");
                    launch();
                } else {
                    System.out.println("\"" + selected + "\" is not a valid option. Please try again.");
                }

            } while (!(selected.equals("1") || selected.equals("2") || selected.equalsIgnoreCase("CLI") || selected.equalsIgnoreCase("GUI")));
        } catch (Exception e) {
            System.err.println("An error occured!");
            System.err.println(e.getMessage());
        }
        try {
            do {
                System.out.println("Now please choose between Socket technology and RMI technology:");
                System.out.println("[1] Socket - [2] RMI");
                selected = scanner.nextLine().trim();

                if (selected.equals("1") || selected.equals("Socket")) {
                    System.out.println("You have selected Socket technology.");
                    client = new SocketClient("localhost", 5000);
                    client.sendPacket(new InfoPacket("Socket chosen!"));
                } else if (selected.equals("2") || selected.equals("RMI")) {
                    System.out.println("You have selected RMI technology.");
                    try {
                        client = new RMIClient("Server", 1099);
                        client.sendPacket(new InfoPacket("RMI chosen!"));
                    } catch (IOException e) {
                        System.err.println("An error occured!");
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.out.println("\"" + selected + "\" is not a valid option. Please try again.");
                }

            } while (!(selected.equals("1") || selected.equals("2") || selected.equalsIgnoreCase("Socket") || selected.equalsIgnoreCase("RMI")));
        } catch (Exception e) {
            System.err.println("An error occured!");
            System.err.println(e.getMessage());
        }

    }
}