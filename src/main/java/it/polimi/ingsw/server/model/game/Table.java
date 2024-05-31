package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.*;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private Deck resourceDeck;
    private Deck goldDeck;
    private Deck objectiveDeck;
    private Deck starterDeck;
    private final List<ObjectiveCard> objectiveCards;
    private final List<Card> cardsOnGround;

    public Table() {
        this.cardsOnGround = new ArrayList<>(4);
        this.objectiveCards = new ArrayList<>(2);
    }

    public Deck getResourceDeck() {
        return resourceDeck;
    }

    public Deck getGoldDeck() {
        return goldDeck;
    }

    public void setResourceDeck(Deck resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

    public void setGoldDeck(Deck goldDeck) {
        this.goldDeck = goldDeck;
    }

    public Deck getObjectiveDeck() {
        return objectiveDeck;
    }

    public Deck getStarterDeck() {
        return starterDeck;
    }

    public void setObjectiveDeck(Deck objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }

    public void setStarterDeck(Deck starterDeck) {
        this.starterDeck = starterDeck;
    }

    public List<Card> getCardsOnGround() {
        return cardsOnGround;
    }

    public void addCardOnGround(Card card) {
        this.cardsOnGround.add(card);
    }

    public void removeCardOnGround(Card card) {
        this.cardsOnGround.remove(card);
    }

    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    public void addObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCards.add(objectiveCard);
    }

}
