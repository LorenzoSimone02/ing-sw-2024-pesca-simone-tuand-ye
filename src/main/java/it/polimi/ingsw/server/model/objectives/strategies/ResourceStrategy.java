package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.util.Collections;

public class ResourceStrategy implements ObjectiveStrategy {

    private final ResourceTypeEnum resourceRequirement;
    int numberOfResource;
    int pointsPerPattern;

    public ResourceStrategy(ResourceTypeEnum resourceRequirement, int numberOfResource, int pointsPerPattern) {
        this.resourceRequirement = resourceRequirement;
        this.numberOfResource = numberOfResource;
        this.pointsPerPattern = pointsPerPattern;
    }

    public int calculatePoints(Player player) {
         return Math.floorDiv(Collections.frequency(player.getResources(), resourceRequirement),numberOfResource) * pointsPerPattern;
    }

    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}