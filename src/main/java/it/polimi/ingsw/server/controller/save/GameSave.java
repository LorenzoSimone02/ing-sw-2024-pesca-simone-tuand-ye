package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameSave implements Serializable {

    private final int id;
    private final int maxPlayers;
    private final String firstPlayer;
    private final String activePlayer;
    private final String gameStatus;

    private final HashMap<String, Integer> playerScores;

    private final Stack<Integer> resourceDeck;
    private final Stack<Integer> goldDeck;
    private final Stack<Integer> objectiveDeck;
    private final Stack<Integer> starterDeck;
    private final ArrayList<Integer> objectiveCards;
    private final ArrayList<Integer> cardsOnGround;

    public GameSave(Game game) {
        this.id = game.getInfo().getId();
        this.maxPlayers = game.getInfo().getMaxPlayers();
        this.firstPlayer = game.getInfo().getFirstPlayer().getUsername();
        this.activePlayer = game.getInfo().getActivePlayer().getUsername();
        this.gameStatus = game.getInfo().getGameStatus().toString();

        this.resourceDeck = new Stack<>();
        for (Card card : game.getTable().getResourceDeck().getCards()) {
            this.resourceDeck.push(card.getId());
        }
        this.goldDeck = new Stack<>();
        for (Card card : game.getTable().getGoldDeck().getCards()) {
            this.goldDeck.push(card.getId());
        }
        this.objectiveDeck = new Stack<>();
        for (Card card : game.getTable().getObjectiveDeck().getCards()) {
            this.objectiveDeck.push(card.getId());
        }
        this.starterDeck = new Stack<>();
        for (Card card : game.getTable().getStarterDeck().getCards()) {
            this.starterDeck.push(card.getId());
        }
        this.objectiveCards = new ArrayList<>(2);
        for (ObjectiveCard objectiveCard : game.getTable().getObjectiveCards()) {
            this.objectiveCards.add(objectiveCard.getId());
        }
        this.cardsOnGround = new ArrayList<>(4);
        for (Card card : game.getTable().getCardsOnGround()) {
            this.cardsOnGround.add(card.getId());
        }

        this.playerScores = new HashMap<>();
        for (Player player : game.getPlayers()) {
            playerScores.put(player.getUsername(), player.getScore());
        }
    }

    public int getId() {
        return id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public Stack<Integer> getResourceDeck() {
        return resourceDeck;
    }

    public Stack<Integer> getGoldDeck() {
        return goldDeck;
    }

    public Stack<Integer> getObjectiveDeck() {
        return objectiveDeck;
    }

    public Stack<Integer> getStarterDeck() {
        return starterDeck;
    }

    public ArrayList<Integer> getObjectiveCards() {
        return objectiveCards;
    }

    public ArrayList<Integer> getCardsOnGround() {
        return cardsOnGround;
    }

    public HashMap<String, Integer> getPlayerScores() {
        return playerScores;
    }

}
