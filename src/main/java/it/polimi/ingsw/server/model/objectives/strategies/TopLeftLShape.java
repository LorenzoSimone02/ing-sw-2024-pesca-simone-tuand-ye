package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class TopLeftLShape implements ObjectiveStrategy {

    private CardColorEnum columnCardsColor;
    private CardColorEnum diagonalCardColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        int points = 0;
        int counter = 0;
        for (int j = 1; j < 81; j++) {
            for (int i = 1 ; i < 81 - 1; i ++) {
                if(cards[i][j] != null) {
                    if (counter == 2 && cards[i - 1][j - 1].getColor() == diagonalCardColor) {
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
}


