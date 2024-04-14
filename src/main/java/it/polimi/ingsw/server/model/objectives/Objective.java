package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.card.Card;

public record Objective(ObjectiveStrategy strategy) {

    public int getPoints(Card[][] cards) {
        return strategy.calculatePoints(cards);
    }
}
