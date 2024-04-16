package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final int id;
    private final Table table;
    private final List<Player> players;
    private final ArrayList<ObjectiveCard> objectiveCards;
    private final GameInfo info;
    private GameController controller;

    public Game(int id) {
        this.id = id;
        this.table = new Table();
        this.players = new ArrayList<>();
        this.objectiveCards = new ArrayList<>();
        this.info = new GameInfo(0);
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

    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    public void addObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCards.add(objectiveCard);
    }

    public GameInfo getInfo() {
        return info;
    }

    public GameController getController() {
        return controller;
    }
}
