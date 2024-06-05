package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;

import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        String serverIP = args.length == 1 ? args[0] : "localhost";

        Scanner scanner = new Scanner(System.in);
        String nextLine;
        ClientNetworkHandler networkHandler = null;
        ViewModeEnum viewMode = ViewModeEnum.TUI;

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
            try {
                System.out.println("Now please choose between Socket technology and RMI technology:");
                System.out.println("[1] Socket - [2] RMI");
                nextLine = scanner.nextLine().trim();
                if (nextLine.equals("1") || nextLine.equals("Socket")) {
                    System.out.println("You have selected Socket technology.");
                    networkHandler = new SocketClient(serverIP, 5000);
                } else if (nextLine.equals("2") || nextLine.equals("RMI")) {
                    System.out.println("You have selected RMI technology.");
                    networkHandler = new RMIClient("CodexNaturalisServer", serverIP, 1099);
                } else {
                    System.out.println("\"" + nextLine + "\" is not a valid option. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred while connecting to the Lobby: " + e.getMessage());
                System.exit(1);
            }
        } while (!(nextLine.equals("1") || nextLine.equals("2") || nextLine.equalsIgnoreCase("Socket") || nextLine.equalsIgnoreCase("RMI")));

        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "1500");
        ClientManager clientManager = new ClientManager(networkHandler, viewMode, serverIP);
        clientManager.getGameState().setClientStatus(ClientStatusEnum.LOBBY);
        System.out.println("Successfully connected to the Lobby.\nUse /login to choose an Username.");
        clientManager.runUI();

    }
}