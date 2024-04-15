package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class TopRightLShape implements ObjectiveStrategy {

    private CardColorEnum columnCardsColor;
    private CardColorEnum diagonalCardColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        int points = 0;
        for (int i = 0; i < 81 - 4; i++) {
            for(int j = 1; j < 81; j++) {
                if( cards[i][j].getColor() == diagonalCardColor &&
                    cards[i+1][j-1].getColor() == columnCardsColor &&
                    cards[i+3][j-1].getColor() == columnCardsColor) {
                    points += pointsPerPattern;
                }
            }
        }
        return points;
    }
}

