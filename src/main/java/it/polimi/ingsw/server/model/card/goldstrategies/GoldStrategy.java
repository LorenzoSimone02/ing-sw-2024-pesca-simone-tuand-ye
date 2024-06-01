package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.player.Player;

/**
 * This interface represents a strategy that assigns points to a player
 * based on the type of GoldStrategy of the card that the player has placed on the board
 */
public interface GoldStrategy {

    /**
     * The method calculates the points of the player based on the GoldStrategy of the card
     * @param player the player that has to calculate the points
     * @param xCoord the x coordinate of the card
     * @param yCoord the y coordinate of the card
     * @return the points of the player based on the GoldStrategy of the card
     */
    int calculatePoints(Player player, int xCoord, int yCoord);
}
