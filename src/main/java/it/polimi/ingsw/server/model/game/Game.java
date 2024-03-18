package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.objectives.Objective;
import it.polimi.ingsw.server.model.player.Player;

import java.util.List;

public class Game {
    private int id;
    private Table table;
    private List<Player> players;
    private Objective objective;
    private GameInfo info;

    public Game(){

    }

    public int getId() {
        return id;
    }

    public Table getTable() {
        return table;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Objective getObjective() {
        return objective;
    }

    public GameInfo getInfo() {
        return info;
    }
    public void initGame(){

    }
    public void AddPlayer(Player player){
        this.players.add(player);
    }
}
