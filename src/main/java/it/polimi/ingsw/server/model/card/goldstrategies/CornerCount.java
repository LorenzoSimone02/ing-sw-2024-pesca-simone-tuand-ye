package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.player.Player;

/**
 * This class implements the GoldStrategy of the GoldCard
 * that assigns points to a player based on the number of corners the placed card has covered
 */
public class CornerCount implements GoldStrategy {

    /**
     * The base points that the player gets for each corner that the placed card has covered
     */
    private final int basePoints;

    /**
     * Constructor of the class
     * @param basePoints the base points that the player gets for each corner that the placed card has covered
     */
    public CornerCount(int basePoints) {
        this.basePoints = basePoints;
    }

    /**
     * The method calculates the points that the player gets based on the number of corners that the placed card has covered
     * @param player the player that gets the points
     * @param xCoord the x coordinate of the card that the player has placed on the board
     * @param yCoord the y coordinate of the card that the player has placed on the board
     * @return the points that the player gets
     */
    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        int cornerCounter = 0;
        Card[][] cards = player.getCards();

        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (cards[xCoord + i][yCoord + j] != null) {
                    cornerCounter++;
                }
            }
        }
        return cornerCounter * basePoints;
    }
}
