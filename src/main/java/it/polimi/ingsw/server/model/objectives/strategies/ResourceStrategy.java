package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.util.Collections;

/**
 * This class implements the ObjectiveStrategy and represents the strategy for the objective "RESOURCE_XXX".
 * It calculates the points of the player based on the number of the specified resource types the player has in their card matrix
 */
public class ResourceStrategy implements ObjectiveStrategy {

    /**
     * The resource type that the player must have in their card matrix
     */
    private final ResourceTypeEnum resourceRequirement;

    /**
     * The number of resource that the player must have in their card matrix
     */
    private final int numberOfResource;

    /**
     * The number of points that the player gets for each pattern of the specified resource types in their card matrix
     */
    private final int pointsPerPattern;

    /**
     * Constructor of the class
     * @param resourceRequirement the resource type that the player must have in their card matrix
     * @param numberOfResource the number of resource that the player must have in their card matrix
     * @param pointsPerPattern the number of points that the player gets for each pattern of the specified resource types
     */
    public ResourceStrategy(ResourceTypeEnum resourceRequirement, int numberOfResource, int pointsPerPattern) {
        this.resourceRequirement = resourceRequirement;
        this.numberOfResource = numberOfResource;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number
     *      of the specified resource types the player has in their card matrix
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of the specified resource types the player has in their card matrix
     */
    public int calculatePoints(Player player) {
        Resource scoringResource = new Resource(ResourceTypeEnum.FUNGI);
        return Math.floorDiv(Collections.frequency(player.getResources(), scoringResource), numberOfResource) * pointsPerPattern;
    }

    /**
     * This method returns the number of points that the player gets for each pattern
     *      of the specified resource types in their card matrix
     * @return the number of points that the player gets for each pattern of the specified resource types in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
