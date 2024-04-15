package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.card.Card;

public record Objective(ObjectiveType type) {

    public ObjectiveType getType() {
        return type;
    }

    public int getPoints(Card[][] cards) {
        return type.getStrategy().calculatePoints(cards);
    }
}
