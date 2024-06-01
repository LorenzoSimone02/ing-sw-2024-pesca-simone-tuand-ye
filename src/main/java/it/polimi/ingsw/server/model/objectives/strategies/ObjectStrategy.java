package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.Collections;
import java.util.List;

/**
 * This class implements the ObjectiveStrategy and represents the strategy for the objective "OBJECT_XXX".
 * It calculates the points of the player based on the number of the specified object types the player has in their card matrix
 */
public class ObjectStrategy implements ObjectiveStrategy {

    /**
     * The list of object types that the player must have in their card matrix
     */
    private final List<ObjectTypeEnum> objectRequirement;

    /**
     * The number of points that the player gets for each pattern of the specified object types in their card matrix
     */
    private final int pointsPerPattern;

    /**
     * Constructor of the class
     * @param objectRequirement the list of object types that the player must have in their card matrix
     * @param pointsPerPattern the number of points that the player gets for each pattern of the specified object types
     */
    public ObjectStrategy(List<ObjectTypeEnum> objectRequirement, int pointsPerPattern) {
        this.objectRequirement = objectRequirement;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number
     *      of the specified object types the player has in their card matrix
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of the specified object types the player has in their card matrix
     */
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

    /**
     * This method returns the number of points that the player gets for each pattern
     *      of the specified object types in their card matrix
     * @return the number of points that the player gets for each pattern of the specified object types in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
