package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.List;

public class ObjectStrategy implements ObjectiveStrategy {

    private List<ObjectTypeEnum> objectRequirment;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        return 0;
    }
}
