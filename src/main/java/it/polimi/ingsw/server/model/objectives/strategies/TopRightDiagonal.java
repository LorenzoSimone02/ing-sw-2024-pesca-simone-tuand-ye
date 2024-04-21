package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;

public class TopRightDiagonal implements ObjectiveStrategy {

    private CardColorEnum CardsColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        return 0;
    }
}
