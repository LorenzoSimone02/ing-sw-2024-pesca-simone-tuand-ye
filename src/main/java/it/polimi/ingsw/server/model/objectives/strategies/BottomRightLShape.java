package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class BottomRightLShape implements ObjectiveStrategy {

    private CardColorEnum columnCardsColor;
    private CardColorEnum diagonalCardColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        int points = 0;
        for (int i = 0; i < 81 - 4; i++) {
            for (int j = 0 ; j < 81 - 1; j++) {
                if( cards[i][j].getColor() == columnCardsColor &&
                    cards[i+2][j].getColor() == columnCardsColor &&
                    cards[i+3][j+1].getColor() == diagonalCardColor) {
                    points = points + pointsPerPattern;
                }
            }
        }
        return points;
    }
}
