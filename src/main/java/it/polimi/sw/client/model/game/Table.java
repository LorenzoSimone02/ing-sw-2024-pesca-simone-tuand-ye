package it.polimi.sw.client.model.game;

import it.polimi.sw.client.model.card.Deck;
import it.polimi.sw.client.model.objectives.Objective;

import java.util.List;

public class Table {
    private ScoreTrack scoreTrack;
    private List<Deck> decks;
    private List<Objective> objectives;

    public ScoreTrack getScoreTrack() {
        return scoreTrack;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }
}
