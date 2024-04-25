package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.HashMap;

public class GameSave implements Serializable {

    private transient final Game game;
    private final int id;
    private final HashMap<String, Integer> playerScores;


    public GameSave(Game game) {
        this.game = game;
        this.id = game.getInfo().getId();
        this.playerScores = new HashMap<>();
        for (Player player : game.getPlayers()) {
            playerScores.put(player.getUsername(), player.getScore());
        }
    }

    public Game getGame() {
        return game;
    }

    public int getId() {
        return id;
    }

    public HashMap<String, Integer> getPlayerScores() {
        return playerScores;
    }

}
