package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

import java.util.Collections;
import java.util.HashMap;
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
     *
     * @param objectRequirement the list of object types that the player must have in their card matrix
     * @param pointsPerPattern  the number of points that the player gets for each pattern of the specified object types
     */
    public ObjectStrategy(List<ObjectTypeEnum> objectRequirement, int pointsPerPattern) {
        this.objectRequirement = objectRequirement;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number
     * of the specified object types the player has in their card matrix
     *
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of the specified object types the player has in their card matrix
     */
    public int calculatePoints(Player player) {
        Object scoringObject;
        HashMap<ObjectTypeEnum, Integer> objectRequirementsMap = new HashMap<>();
        int inkwellFrequency = 0, quillFrequency = 0, manuscriptFrequency = 0;

        for (ObjectTypeEnum objectTypeEnum : objectRequirement) {
            objectRequirementsMap.put(objectTypeEnum, objectRequirementsMap.getOrDefault(objectTypeEnum, 0) + 1);
        }

        for (ObjectTypeEnum objectTypeEnum : objectRequirementsMap.keySet()) {
            scoringObject = new Object(objectTypeEnum);

            switch (objectTypeEnum) {
                case INKWELL -> inkwellFrequency = Math.floorDiv(Collections.frequency(player.getObjects(), scoringObject), objectRequirementsMap.get(objectTypeEnum));
                case QUILL -> quillFrequency = Math.floorDiv(Collections.frequency(player.getObjects(), scoringObject), objectRequirementsMap.get(objectTypeEnum));
                case MANUSCRIPT -> manuscriptFrequency = Math.floorDiv(Collections.frequency(player.getObjects(), scoringObject), objectRequirementsMap.get(objectTypeEnum));
            }
        }

        if (objectRequirementsMap.keySet().size() == 1) {
            return Math.max(Math.max(inkwellFrequency, quillFrequency), manuscriptFrequency) * pointsPerPattern;
        } else {
            return Math.min(Math.min(inkwellFrequency, quillFrequency), manuscriptFrequency) * pointsPerPattern;
        }
    }

    /**
     * This method returns the number of points that the player gets for each pattern
     * of the specified object types in their card matrix
     *
     * @return the number of points that the player gets for each pattern of the specified object types in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
