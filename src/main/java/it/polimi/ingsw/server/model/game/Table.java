package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the table of the game
 */
public class Table {

    /**
     * The deck of resource cards
     */
    private Deck resourceDeck;

    /**
     * The deck of gold cards
     */
    private Deck goldDeck;

    /**
     * The deck of objective cards
     */
    private Deck objectiveDeck;

    /**
     * The deck of starter cards
     */
    private Deck starterDeck;

    /**
     * The list of objective cards on the table
     */
    private final List<ObjectiveCard> objectiveCards;

    /**
     * The list of cards on the ground
     */
    private final List<Card> cardsOnGround;

    /**
     * Constructor of the class
     */
    public Table() {
        this.cardsOnGround = new ArrayList<>(4);
        this.objectiveCards = new ArrayList<>(2);
    }

    /**
     * The method returns the deck of resource cards
     * @return the deck of resource cards
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * The method returns the deck of gold cards
     * @return the deck of gold cards
     */
    public Deck getGoldDeck() {
        return goldDeck;
    }

    /**
     * The method sets the deck of resource cards
     * @param resourceDeck the deck of resource cards
     */
    public void setResourceDeck(Deck resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

    /**
     * The method sets the deck of gold cards
     * @param goldDeck the deck of gold cards
     */
    public void setGoldDeck(Deck goldDeck) {
        this.goldDeck = goldDeck;
    }

    /**
     * The method returns the deck of objective cards
     * @return the deck of objective cards
     */
    public Deck getObjectiveDeck() {
        return objectiveDeck;
    }

    /**
     * The method returns the deck of starter cards
     * @return the deck of starter cards
     */
    public Deck getStarterDeck() {
        return starterDeck;
    }

    /**
     * The method sets the deck of objective cards
     * @param objectiveDeck the deck of objective cards
     */
    public void setObjectiveDeck(Deck objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }

    /**
     * The method sets the deck of starter cards
     * @param starterDeck the deck of starter cards
     */
    public void setStarterDeck(Deck starterDeck) {
        this.starterDeck = starterDeck;
    }

    /**
     * The method returns the list of cards on the ground
     * @return the list of cards on the ground
     */
    public List<Card> getCardsOnGround() {
        return cardsOnGround;
    }

    /**
     * The method adds a card on the ground
     * @param card the card to add on the ground
     */
    public void addCardOnGround(Card card) {
        this.cardsOnGround.add(card);
    }

    /**
     * The method removes a card on the ground
     * @param card the card to remove from the ground
     */
    public void removeCardOnGround(Card card) {
        this.cardsOnGround.remove(card);
    }

    /**
     * The method returns the list of objective cards on the table
     * @return the list of objective cards on the table
     */
    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    /**
     * The method adds an objective card on the table
     * @param objectiveCard the objective card to add on the table
     */
    public void addObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCards.add(objectiveCard);
    }

}
