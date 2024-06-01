package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.player.Player;

/**
 * This class represents the strategy that assigns points to a player
 * based on the number of points showed on the placed card itself
 */
public class PlainPoints implements GoldStrategy {

    /**
     * The base points that the player gets for the placed card
     */
    private final int basePoints;

    /**
     * Constructor of the class
     * @param basePoints the base points that the player gets for the placed card
     */
    public PlainPoints(int basePoints) {
        this.basePoints = basePoints;
    }

    /**
     * The method calculates the points that the player gets based on the number of points showed on the placed card
     * @param player the player that gets the points
     * @param xCoord the x coordinate of the card that the player has placed on the board
     * @param yCoord the y coordinate of the card that the player has placed on the board
     * @return the points that the player gets
     */
    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        return basePoints;
    }
}
