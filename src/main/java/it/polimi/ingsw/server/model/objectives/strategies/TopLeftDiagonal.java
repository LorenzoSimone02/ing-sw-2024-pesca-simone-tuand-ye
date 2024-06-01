package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;

/**
 * This class implements the ObjectiveStrategy and represents the strategy for the objective "TOP_LEFT_DIAGONAL".
 * It calculates the points of the player based on the number of top left diagonals
 *      of the specified color in their card matrix
 */
public class TopLeftDiagonal implements ObjectiveStrategy {

    /**
     * The color of the cards that form the diagonal
     */
    private final CardColorEnum CardsColor;

    /**
     * The number of points that the player gets for each top left diagonals of the specified color in their card matrix
     */
    private final int pointsPerPattern;

    /**
     * Constructor of the class
     * @param cardsColor the color of the cards that form the diagonal
     * @param pointsPerPattern the number of points that the player gets for each top left diagonals
     *                         of the specified color in their card matrix
     */
    public TopLeftDiagonal(CardColorEnum cardsColor, int pointsPerPattern) {
        CardsColor = cardsColor;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number of top left diagonals
     *      of the specified color in their card matrix
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of top left diagonals
     *      of the specified color in their card matrix
     */
    public int calculatePoints(Player player) {
        ResourceCard[][] cards = player.getCards();
        int points = 0;
        int counter = 0;
        int len = cards.length;
        int itemsInDiagonal = 0;
        int diagonalLines = (len + len) - 1;
        int midPoint = (diagonalLines / 2) + 1;
        for (int i = 1; i <= diagonalLines; i++){
            int rowIndex;
            int columnIndex;

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (len - 1) - j;
                    columnIndex = j;
                    if(cards[rowIndex][columnIndex] != null){
                        if(cards[rowIndex][columnIndex].getColor() == CardsColor && counter + 1 < 3) {
                            counter ++;
                        }
                        else if(cards[rowIndex][columnIndex].getColor() == CardsColor && counter + 1 == 3) {
                            points = points + pointsPerPattern;
                            counter = 0;
                        }
                        else {
                            counter = 0;
                        }
                    }
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = j;
                    columnIndex = (i - len) + j;
                    if(cards[rowIndex][columnIndex] != null){
                        if(cards[rowIndex][columnIndex].getColor() == CardsColor && counter + 1 < 3) {
                            counter ++;
                        }
                        else if(cards[rowIndex][columnIndex].getColor() == CardsColor && counter + 1 == 3) {
                            points = points + pointsPerPattern;
                            counter = 0;
                        }
                        else {
                            counter = 0;
                        }
                    }
                }
            }
        }
        return points;
    }

    /**
     * This method returns the number of points that the player gets for each top left diagonals
     *      of the specified color in their card matrix
     * @return the number of points that the player gets for each top left diagonals of the specified color in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
