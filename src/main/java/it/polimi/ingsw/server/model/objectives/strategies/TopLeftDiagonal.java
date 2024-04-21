package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;

public class TopLeftDiagonal implements ObjectiveStrategy {

    private CardColorEnum CardsColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        int points = 0;
        int counter = 0;
        for (int j = cards.length - 1; j >= 0; j--) {
            for (int i = 0; i < cards.length - j - 1 ; i++) {
                if(cards[j+i][i].getColor() == CardsColor && counter < 3) {
                    counter ++;
                } else if (cards[j+i][i].getColor() == CardsColor && counter == 3) {
                    points = points + pointsPerPattern;
                }
                else {
                    counter = 0;
                }
            }
            counter = 0;
        }
        return points;
    }

}
