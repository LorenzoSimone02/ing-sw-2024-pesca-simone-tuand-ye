package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;

public class ServerMain {

    public static void main(String[] args) {

        ServerNetworkHandler networkHandler = new ServerNetworkHandler("Server", 1099, 5000);
        networkHandler.start();

        System.out.println("Server ready");
    }
}
