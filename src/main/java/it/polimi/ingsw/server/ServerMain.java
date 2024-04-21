package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;

public class ServerMain {

    public static int gameNumber;

    public static void main(String[] args) {
        gameNumber = 0;

        ServerNetworkHandler networkHandler = new ServerNetworkHandler("Server", 1099, 5000);
        networkHandler.start();

        System.out.println("Server ready");
    }
}
