package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The class that represents the game status saving in case of disconnection
 */
public class GameSave implements Serializable {

    /**
     * The saved maximum number of players
     */
    private final int maxPlayers;

    /**
     * The saved username of the first player
     */
    private final String firstPlayer;

    /**
     * The saved username of the active player
     */
    private final String activePlayer;

    /**
     * The saved status of the game
     */
    private final String gameStatus;

    /**
     * The list of the saved player status
     */
    private final ArrayList<PlayerSave> playerSaves;

    /**
     * The saved status of the resource deck stack
     */
    private final Stack<CardSave> resourceDeck;

    /**
     * The saved status of the gold deck stack
     */
    private final Stack<CardSave> goldDeck;

    /**
     * The saved status of the objective deck stack
     */
    private final Stack<CardSave> objectiveDeck;

    /**
     * The saved status of the starter deck stack
     */
    private final Stack<CardSave> starterDeck;

    /**
     * The list of the saved objective cards
     */
    private final ArrayList<CardSave> objectiveCards;

    /**
     * The list of the saved cards on ground
     */
    private final ArrayList<CardSave> cardsOnGround;

    /**
     * The constructor of the class
     * @param game the game
     */
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

        this.playerSaves = new ArrayList<>(4);
        for (Player player : game.getPlayers()) {
            playerSaves.add(new PlayerSave(player));
        }
        for(Player player : game.getOfflinePlayers()){
            playerSaves.add(new PlayerSave(player));
        }
    }

    /**
     * The method returns the saved maximum number of players
     * @return the saved maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * The method returns saved the username of the first player
     * @return the saved username of the first player
     */
    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * The method returns the saved username of the active player
     * @return the saved username of the active player
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * The method returns the saved status of the game
     * @return the saved status of the game
     */
    public String getGameStatus() {
        return gameStatus;
    }

    /**
     * The method returns the saved resource deck stack
     * @return the saved resource deck stack
     */
    public Stack<CardSave> getResourceDeck() {
        return resourceDeck;
    }

    /**
     * The method returns the saved gold deck stack
     * @return the saved gold deck stack
     */
    public Stack<CardSave> getGoldDeck() {
        return goldDeck;
    }

    /**
     * The method returns the saved objective deck stack
     * @return the saved objective deck stack
     */
    public Stack<CardSave> getObjectiveDeck() {
        return objectiveDeck;
    }

    /**
     * The method returns the saved starter deck stack
     * @return the saved starter deck stack
     */
    public Stack<CardSave> getStarterDeck() {
        return starterDeck;
    }

    /**
     * The method returns the list of the saved player status
     * @return the list of the saved player status
     */
    public ArrayList<CardSave> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     * The method returns the list of the saved cards on ground
     * @return the list of the saved cards on ground
     */
    public ArrayList<CardSave> getCardsOnGround() {
        return cardsOnGround;
    }

    /**
     * The method returns the list of the saved player status
     * @return the list of the saved player status
     */
    public ArrayList<PlayerSave> getPlayerSaves() {
        return playerSaves;
    }
}
