package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.Player;

/**
 * This class represents the strategy that assigns points to a player
 * based on the number of objects of a certain type the player already has on the board
 */
public class ObjectCount implements GoldStrategy {

    /**
     * The base points that the player gets for each object of the needed type
     */
    private final int basePoints;

    /**
     * The type of object that the player needs to have on the board
     */
    private final ObjectTypeEnum objectNeeded;

    /**
     * Constructor of the class
     * @param basePoints the base points that the player gets for each object of the needed type
     * @param objectNeeded the type of object that the player needs to have on the board
     */
    public ObjectCount(int basePoints, ObjectTypeEnum objectNeeded) {
        this.basePoints = basePoints;
        this.objectNeeded = objectNeeded;
    }

    /**
     * The method calculates the points that the player gets based on the number of objects of the needed type
     * that the player has on the board
     * @param player the player that gets the points
     * @param xCoord the x coordinate of the card that the player has placed on the board
     * @param yCoord the y coordinate of the card that the player has placed on the board
     * @return the points that the player gets
     */
    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        int objectCounter = 0;
        for (Object obj: player.getObjects()) {
            if (obj.type().equals(objectNeeded)) {
                objectCounter++;
            }
        }

        return objectCounter * basePoints;
    }
}
