package it.polimi.ingsw.client.controller.clientstate;

import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;

import java.util.LinkedList;

/**
 * The class represents the state of a player
 */
public class PlayerState {

    /**
     * The username of a player
     */
    private final String username;

    /**
     * The token color of a player
     */
    private String tokenColor;

    /**
     * The placed cards of a player
     */
    private final ResourceCard[][] cardsPlaced;

    /**
     * The ordered placed cards of a player
     */
    private final LinkedList<ResourceCard> orderedCardsPlaced;

    /**
     * The score of a player
     */
    int score;

    /**
     * The constructor of the class
     *
     * @param username the player's username
     */
    public PlayerState(String username) {
        this.username = username;
        this.cardsPlaced = new ResourceCard[81][81];
        this.orderedCardsPlaced = new LinkedList<>();
        this.score = 0;
    }

    /**
     * The method returns the player's username
     *
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method returns the player's score
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * The method sets the player's score
     *
     * @param score the player's score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * The method returns the player's token color
     *
     * @return the player's token color
     */
    public String getTokenColor() {
        return tokenColor;
    }

    /**
     * The method sets the player's token color
     *
     * @param tokenColor the player's token color
     */
    public void setTokenColor(String tokenColor) {
        this.tokenColor = tokenColor;
    }

    /**
     * The method returns the player's placed cards
     *
     * @return the player's placed cards
     */
    public ResourceCard[][] getCardsPlaced() {
        return cardsPlaced;
    }

    /**
     * The method returns the player's ordered placed cards
     *
     * @return the player's ordered placed cards
     */
    public LinkedList<ResourceCard> getOrderedCardsPlaced() {
        return orderedCardsPlaced;
    }

    /**
     * The method adds a card to the player's ordered placed cards
     *
     * @param card the card to add
     */
    public void addOrderedCard(ResourceCard card) {
        orderedCardsPlaced.add(card);
    }

    /**
     * The method sets a card to the player's placed cards matrix
     *
     * @param card the card to set
     * @param x    the x coordinate
     * @param y    the y coordinate
     */
    public void setCardPlaced(ResourceCard card, int x, int y) {
        cardsPlaced[x][y] = card;
    }

    /**
     * The method sets the player's starter card at the center of the player's placed cards matrix
     *
     * @param card the starter card
     */
    public void setStarterCard(StarterCard card) {
        setCardPlaced(card, 40, 40);
    }

    /**
     * The method returns the player's starter card
     *
     * @return the player's starter card
     */
    public StarterCard getStarterCard() {
        return (StarterCard) cardsPlaced[40][40];
    }
}
