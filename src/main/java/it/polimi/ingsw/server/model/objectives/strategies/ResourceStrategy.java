package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ResourceStrategy implements ObjectiveStrategy {

    private ResourceTypeEnum resourceRequirment;

    int numberOfResource;
    int pointsPerPattern;

    public int calculatePoints(Player player) {
         return Math.floorDiv(Collections.frequency(player.getResources(), resourceRequirment),numberOfResource) * pointsPerPattern;
    }
}