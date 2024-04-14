package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.*;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private Deck resouceDeck;
    private Deck goldDeck;
    private final List<ObjectiveCard> objectiveCards;
    private final List<StarterCard> starterCards;
    private final List<Card> cardsOnGround;

    public Table() {
        this.objectiveCards = new ArrayList<>();
        this.starterCards = new ArrayList<>();
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

    public List<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    public List<StarterCard> getStarterCards() {
        return starterCards;
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

    public void addStarterCard(StarterCard card) {
        this.starterCards.add(card);
    }

    public void addObjectiveCard(ObjectiveCard objective) {
        this.objectiveCards.add(objective);
    }

}
