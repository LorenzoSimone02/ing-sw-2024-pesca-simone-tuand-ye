package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.player.Player;

/**
 * This interface represents a strategy that assigns points to a player based on the objective type they have
 */
public interface ObjectiveStrategy {

    /**
     * This method calculates the points of the player based on the objective type they have
     * @param player the player that has to calculate the points
     * @return the points of the player based on the objective type they have
     */
    int calculatePoints(Player player);

    /**
     * This method returns the number of points that the player gets based on the objective type they have
     * @return the number of points that the player gets based on the objective type they have
     */
    int getPointsPerPattern();
}
