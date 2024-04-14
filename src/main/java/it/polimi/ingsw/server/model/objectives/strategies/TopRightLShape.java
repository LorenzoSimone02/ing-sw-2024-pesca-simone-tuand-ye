package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class TopRightLShape implements ObjectiveStrategy {

    private ResourceTypeEnum resourceRequirment;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        return 0;
    }
}

