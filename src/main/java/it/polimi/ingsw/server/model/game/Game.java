package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.objectives.Objective;
import it.polimi.ingsw.server.model.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Game {
    private final int id;
    private final Table table;
    private List<Player> players;
    private Player activePlayer;
    private Objective objective;
    private GameInfo info;

    public Game(int id, Table table){
        this.id = id;
        this.table = table;
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

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective){
        this.objective = objective;
    }

    public GameInfo getInfo() {
        return info;
    }

    public void setInfo(GameInfo info) {
        this.info = info;
    }

    public void assignStarterCard(){
        for(Player player : players){
        }
    }

    public void assignCommonObjectives(){

    }

    public Player nextPlayer(Player activePlayer){
        int index = players.indexOf(activePlayer);
        return players.get((index + 1) % players.size());
    }

    public void chooseFirstPlayer(){
        Player first = players.get(new Random().nextInt(players.size()));
        first.setFirst(true);
        activePlayer = first;
        this.getInfo().setFirstPlayer(first);
    }

    public void startGame(){

    }

    public void endGame(){

    }

    public Optional<Player> getPlayerByNick(String nick) {
        for (Player p : players) {
            if(p.getNickname().equalsIgnoreCase(nick)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }


}
