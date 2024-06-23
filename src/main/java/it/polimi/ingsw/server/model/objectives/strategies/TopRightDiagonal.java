package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;

/**
 * This class implements the ObjectiveStrategy and represents the strategy for the objective "TOP_RIGHT_DIAGONAL".
 * It calculates the points of the player based on the number of top right diagonals
 *      of the specified color in their card matrix
 */
public class TopRightDiagonal implements ObjectiveStrategy {

    /**
     * The color of the cards that form the diagonal
     */
    private final CardColorEnum CardsColor;

    /**
     * The number of points that the player gets for each top right diagonals of the specified color in their card matrix
     */
    private final int pointsPerPattern;

    /**
     * Constructor of the class
     * @param cardsColor the color of the cards that form the diagonal
     * @param pointsPerPattern the number of points that the player gets for each top right diagonals
     *                         of the specified color in their card matrix
     */
    public TopRightDiagonal(CardColorEnum cardsColor, int pointsPerPattern) {
        CardsColor = cardsColor;
        this.pointsPerPattern = pointsPerPattern;
    }

    /**
     * This method calculates the points of the player based on the number of top right diagonals
     *      of the specified color in their card matrix
     * @param player the player whose points are being calculated
     * @return the points of the player based on the number of top right diagonals
     *      of the specified color in their card matrix
     */
    public int calculatePoints(Player player) {
        ResourceCard[][] cards = player.getCards();
        ArrayList<ResourceCard> scoringCards = new ArrayList<>();

        int points = 0;

        for (int i = 80; i >= 2; i--) {
            for (int j = 0; j <= 78; j++) {
                if (cards[i][j] != null && !(cards[i][j] instanceof StarterCard)) {
                    if (cards[i][j].getColor() == CardsColor && !scoringCards.contains(cards[i][j])) {
                        if (cards[i - 1][j + 1] != null && !(cards[i - 1][j + 1] instanceof StarterCard) && cards[i - 1][j + 1].getColor() == CardsColor) {
                            if (cards[i - 2][j + 2] != null && !(cards[i - 2][j + 2] instanceof StarterCard) && cards[i - 2][j + 2].getColor() == CardsColor) {
                                points = points + pointsPerPattern;

                                scoringCards.add(cards[i][j]);
                                scoringCards.add(cards[i - 1][j + 1]);
                                scoringCards.add(cards[i - 2][j + 2]);

                            }
                        }
                    }
                }
            }
        }
        return points;
    }

    /**
     * This method returns the number of points that the player gets for each top right diagonals
     *      of the specified color in their card matrix
     * @return the number of points that the player gets for each top right diagonals of the specified color in their card matrix
     */
    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
