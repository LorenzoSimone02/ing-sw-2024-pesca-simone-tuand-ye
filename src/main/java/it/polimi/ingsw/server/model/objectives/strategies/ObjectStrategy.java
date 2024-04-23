package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.List;

public class ObjectStrategy implements ObjectiveStrategy {

    private final List<ObjectTypeEnum> objectRequirement;
    int pointsPerPattern;

    public ObjectStrategy(List<ObjectTypeEnum> objectRequirement, int pointsPerPattern) {
        this.objectRequirement = objectRequirement;
        this.pointsPerPattern = pointsPerPattern;
    }

    public int calculatePoints(Player player) {
        return 0;
    }
}
