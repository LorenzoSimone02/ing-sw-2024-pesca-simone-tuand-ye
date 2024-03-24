package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.Deck;
import it.polimi.ingsw.server.model.objectives.Objective;

import java.util.List;

public class Table {
    private List<Deck> decks;
    private List<Objective> objectives;
    private List<Card> cardsOnGround;

    public Table(){
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void addDeck(Deck deck){
        this.decks.add(deck);
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void addObjective(Objective objective){
        this.objectives.add(objective);
    }

    public List<Card> getCardsOnGround() {
        return cardsOnGround;
    }

    public void addCardOnGround(Card card){
        this.cardsOnGround.add(card);
    }

    public void removeCardOnGround(Card card){
        this.cardsOnGround.remove(card);
    }
}
