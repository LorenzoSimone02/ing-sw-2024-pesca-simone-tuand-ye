package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.model.game.Game;

public class ServerMain {

    public static int gameNumber;

    public static void main(String[] args) {
        ServerNetworkHandler networkHandler = new ServerNetworkHandler("Server", 1099, 5000);
        networkHandler.start();

        System.out.println("Server ready");

        gameNumber = 0;

        Game game = new Game(++gameNumber);
    }
}
