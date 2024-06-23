package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;

/**
 * This class implements the ObjectiveStrategy and represents the strategy for the objective "BOTTOM_RIGHT_L_SHAPE".
 * It calculates the points of the player based on the number of bottom right L shapes
 *      of the specified color in their card matrix
 */
public class BottomRightLShape implements ObjectiveStrategy {

    /**
     * The color of the 2 cards that form the column of the L shape
     */
    private final CardColorEnum columnCardsColor;

    /**
     * The color of the card that's at the bottom right of the L shape
     */
    private final CardColorEnum diagonalCardColor;

    /**
     * The number of points that the player gets for each bottom right L shapes of the specified color in their card matrix
     */
    private final int pointsPerPattern;

    /**
     * Constructor of the class
     * @param columnCardsColor the color of the 2 cards that form the column of the L shape
     * @param diagonalCardColor the color of the card that's at the bottom right of the L shape
     * @param pointsPerPattern the number of points that the player gets for each bottom right L shapes
     *                         of the specified color in their card matrix
     */
    public BottomRightLShape(CardColorEnum columnCardsColor, CardColorEnum diagonalCardColor, int pointsPerPattern) {
        this.columnCardsColor = columnCardsColor;
        this.diagonalCardColor = diagonalCardColor;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number of bottom right L shapes
     *    of the specified color in their card matrix
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of bottom right L shapes
     *      of the specified color in their card matrix
     */
    public int calculatePoints(Player player) {
        ResourceCard[][] cards = player.getCards();
        int points = 0;
        int counter = 0;
        for (int j = 0; j < 81-1; j++) {
            for (int i = 0 ; i < 81 - 1; i ++) {
                if(cards[i][j] != null) {
                    if (counter > 0 && cards[i + 1][j + 1] != null && cards[i + 1][j + 1].getColor() == diagonalCardColor && cards[i - 2][j] != null && cards[i - 2][j].getColor() == columnCardsColor) {
                        points = points + pointsPerPattern;
                        counter = 0;
                    }
                    else if (cards[i][j].getColor() == columnCardsColor) {
                        counter++;
                    }
                    else {counter = 0;}
                }
            }
        }
        return points;
    }

    /**
     * This method returns the number of points that the player gets for each bottom right L shapes
     *      of the specified color in their card matrix
     * @return the number of points that the player gets for each bottom right L shapes
     *      of the specified color in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
