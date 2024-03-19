package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.Deck;
import it.polimi.ingsw.server.model.objectives.Objective;

import java.util.List;

public class Table {
    private final ScoreTrack scoreTrack;
    private List<Deck> decks;
    private List<Objective> objectives;
    private List<Card> cardsOnGround;

    public Table(ScoreTrack scoreTrack){
        this.scoreTrack = scoreTrack;
    }

    public ScoreTrack getScoreTrack() {
        return scoreTrack;
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

    public void addCardonGround(Card card){
        this.cardsOnGround.add(card);
    }

    public void removeCardonGround(Card card){
        this.cardsOnGround.remove(card);
    }
}
