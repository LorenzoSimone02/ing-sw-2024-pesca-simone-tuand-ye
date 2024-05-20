package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.Collections;
import java.util.List;

public class ObjectStrategy implements ObjectiveStrategy {

    private final List<ObjectTypeEnum> objectRequirement;
    int pointsPerPattern;

    public ObjectStrategy(List<ObjectTypeEnum> objectRequirement, int pointsPerPattern) {
        this.objectRequirement = objectRequirement;
        this.pointsPerPattern = pointsPerPattern;
    }

    public int calculatePoints(Player player) {
        int points;
        int counter = 0;
        for (ObjectTypeEnum objectTypeEnum : objectRequirement) {
            if (counter <= 0) {
                counter = Collections.frequency(player.getObjects(), objectTypeEnum);
            } else {
                counter = Math.min(counter, Collections.frequency(player.getObjects(), objectTypeEnum));
            }
        }
        points = counter * pointsPerPattern;
        return points;
    }

    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
