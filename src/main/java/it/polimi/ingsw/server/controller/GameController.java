package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.model.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.model.exceptions.FullLobbyException;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.util.Optional;

public class GameController {

    private Game game;
    private final ServerNetworkHandler networkHandler;

    public GameController(ServerNetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public Game getGame() {
        return game;
    }

    public ServerNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public synchronized void createGame(int gameId) {
        game = new Game(gameId);
    }

    public synchronized void startGame() {
        game.startGame();
    }

    public synchronized void endGame() {
        game.endGame();
    }

    public void onPlayerJoin(String nickname) {
        try {
            Player player = new Player(nickname, game);
            game.addPlayer(player);
        } catch (DuplicatePlayerException | FullLobbyException ex) {
            System.err.println("An error occured while adding player " + nickname + " to the game: " + ex);
        }
    }

    public void onPlayerLeave(String nickname) {
        Optional<Player> player = game.getPlayerByNick(nickname);
        player.ifPresent(value -> game.removePlayer(value));
    }

    public void beginTurn() {

    }

    public void endTurn() {

    }
}
