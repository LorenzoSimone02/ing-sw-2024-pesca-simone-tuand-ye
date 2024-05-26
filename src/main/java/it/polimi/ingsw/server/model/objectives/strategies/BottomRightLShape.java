package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.player.Player;

public class BottomRightLShape implements ObjectiveStrategy {

    private final CardColorEnum columnCardsColor;
    private final CardColorEnum diagonalCardColor;
    private final int pointsPerPattern;

    public BottomRightLShape(CardColorEnum columnCardsColor, CardColorEnum diagonalCardColor, int pointsPerPattern) {
        this.columnCardsColor = columnCardsColor;
        this.diagonalCardColor = diagonalCardColor;
        this.pointsPerPattern = pointsPerPattern;
    }

    public int calculatePoints(Player player) {
        ResourceCard[][] cards = player.getCards();
        int points = 0;
        int counter = 0;
        for (int j = 0; j < 81-1; j++) {
            for (int i = 0 ; i < 81 - 1; i ++) {
                if(cards[i][j] != null) {
                    if (counter == 2 && cards[i + 1][j + 1].getColor() == diagonalCardColor) {
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

    public int getPointsPerPattern() {
        return pointsPerPattern;
    }
}
