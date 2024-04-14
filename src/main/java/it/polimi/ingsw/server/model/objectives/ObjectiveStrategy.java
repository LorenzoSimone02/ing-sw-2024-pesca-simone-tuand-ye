package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.card.Card;

public interface ObjectiveStrategy {

    int calculatePoints(Card[][] cards);
}
