package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;

/**
 * This class implements the ObjectiveStrategy and represents the strategy for the objective "TOP_RIGHT_L_SHAPE".
 * It calculates the points of the player based on the number of top right L shapes
 *      of the specified color in their card matrix
 */
public class TopRightLShape implements ObjectiveStrategy {

    /**
     * The color of the 2 cards that form the column of the L shape
     */
    private final CardColorEnum columnCardsColor;

    /**
     * The color of the card that's at the top right of the L shape
     */
    private final CardColorEnum diagonalCardColor;

    /**
     * The number of points that the player gets for each top right L shapes of the specified color in their card matrix
     */
    private final int pointsPerPattern;

    /**
     * Constructor of the class
     * @param columnCardsColor the color of the 2 cards that form the column of the L shape
     * @param diagonalCardColor the color of the card that's at the top right of the L shape
     * @param pointsPerPattern the number of points that the player gets for each top right L shapes
     *                         of the specified color in their card matrix
     */
    public TopRightLShape(CardColorEnum columnCardsColor, CardColorEnum diagonalCardColor, int pointsPerPattern) {
        this.columnCardsColor = columnCardsColor;
        this.diagonalCardColor = diagonalCardColor;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number of top right L shapes
     *      of the specified color in their card matrix
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of top right L shapes
     *      of the specified color in their card matrix
     */
    public int calculatePoints(Player player) {
        ResourceCard[][] cards = player.getCards();
        ArrayList<ResourceCard> scoringCards = new ArrayList<>();
        int points = 0;

        for (int j = 0; j < 80; j++) {
            for (int i = 1 ; i < 80; i ++) {
                if (cards[i][j] != null && !(cards[i][j] instanceof StarterCard)) {
                    if (cards[i][j].getColor() == columnCardsColor && !scoringCards.contains(cards[i][j])) {
                        if (cards[i + 2][j] != null && !scoringCards.contains(cards[i + 2][j]) && !(cards[i + 2][j] instanceof StarterCard) && cards[i + 2][j].getColor() == columnCardsColor) {
                            if (cards[i - 1][j + 1] != null && !(cards[i - 1][j + 1] instanceof StarterCard) && cards[i - 1][j + 1].getColor() == diagonalCardColor) {
                                points = points + pointsPerPattern;

                                scoringCards.add(cards[i][j]);
                                scoringCards.add(cards[i + 2][j]);
                                scoringCards.add(cards[i - 1][j + 1]);

                            }
                        }
                    }
                }
            }
        }
        return points;
    }

    /**
     * This method returns the number of points that the player gets for each top right L shapes
     *      of the specified color in their card matrix
     * @return the number of points that the player gets for each top right L shapes of the specified color in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}

