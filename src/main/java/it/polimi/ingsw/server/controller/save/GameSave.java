package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class GameSave implements Serializable {

    private final int maxPlayers;
    private final String firstPlayer;
    private final String activePlayer;
    private final String gameStatus;

    private final ArrayList<PlayerSave> playerSaves;

    private final Stack<CardSave> resourceDeck;
    private final Stack<CardSave> goldDeck;
    private final Stack<CardSave> objectiveDeck;
    private final Stack<CardSave> starterDeck;
    private final ArrayList<CardSave> objectiveCards;
    private final ArrayList<CardSave> cardsOnGround;

    public GameSave(Game game) {
        this.maxPlayers = game.getInfo().getMaxPlayers();
        this.firstPlayer = game.getInfo().getFirstPlayer().getUsername();
        this.activePlayer = game.getInfo().getActivePlayer().getUsername();
        this.gameStatus = game.getInfo().getGameStatus().toString();

        this.resourceDeck = new Stack<>();
        for (Card card : game.getTable().getResourceDeck().getCards()) {
            this.resourceDeck.push(new CardSave(card));
        }
        this.goldDeck = new Stack<>();
        for (Card card : game.getTable().getGoldDeck().getCards()) {
            this.goldDeck.push(new CardSave(card));
        }
        this.objectiveDeck = new Stack<>();
        for (Card card : game.getTable().getObjectiveDeck().getCards()) {
            this.objectiveDeck.push(new CardSave(card));
        }
        this.starterDeck = new Stack<>();
        for (Card card : game.getTable().getStarterDeck().getCards()) {
            this.starterDeck.push(new CardSave(card));
        }
        this.objectiveCards = new ArrayList<>(2);
        for (ObjectiveCard objectiveCard : game.getTable().getObjectiveCards()) {
            this.objectiveCards.add(new CardSave(objectiveCard));
        }
        this.cardsOnGround = new ArrayList<>(4);
        for (Card card : game.getTable().getCardsOnGround()) {
            this.cardsOnGround.add(new CardSave(card));
        }

        this.playerSaves = new ArrayList<>(game.getPlayers().size());
        for (Player player : game.getPlayers()) {
            playerSaves.add(new PlayerSave(player));
        }
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

    public Stack<CardSave> getResourceDeck() {
        return resourceDeck;
    }

    public Stack<CardSave> getGoldDeck() {
        return goldDeck;
    }

    public Stack<CardSave> getObjectiveDeck() {
        return objectiveDeck;
    }

    public Stack<CardSave> getStarterDeck() {
        return starterDeck;
    }

    public ArrayList<CardSave> getObjectiveCards() {
        return objectiveCards;
    }

    public ArrayList<CardSave> getCardsOnGround() {
        return cardsOnGround;
    }

    public ArrayList<PlayerSave> getPlayerSaves() {
        return playerSaves;
    }
}
