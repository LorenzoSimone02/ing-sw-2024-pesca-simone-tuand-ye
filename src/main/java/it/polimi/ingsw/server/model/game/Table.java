package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.*;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private Deck resouceDeck;
    private Deck goldDeck;
    private Deck objectiveDeck;
    private Deck starterDeck;
    private final List<Card> cardsOnGround;

    public Table() {
        this.cardsOnGround = new ArrayList<>();
    }

    public Deck getResourceDeck() {
        return resouceDeck;
    }

    public Deck getGoldDeck() {
        return goldDeck;
    }

    public void setResouceDeck(Deck resouceDeck) {
        this.resouceDeck = resouceDeck;
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
}
