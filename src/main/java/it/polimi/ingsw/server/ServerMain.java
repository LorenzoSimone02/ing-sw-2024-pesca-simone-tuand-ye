package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.game.Game;

import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    private static List<Game> games;

    public static void main(String[] args) {
        System.out.println("Starting server...");

        games = new ArrayList<>();

        System.out.println("Waiting for players...");

        Game game = new Game();

        games.add(game);
    }

    public static List<Game> getGames() {
        return games;
    }
}
