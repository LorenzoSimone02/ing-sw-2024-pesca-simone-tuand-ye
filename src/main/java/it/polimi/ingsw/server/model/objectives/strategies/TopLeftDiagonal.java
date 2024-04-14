package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class TopLeftDiagonal implements ObjectiveStrategy {

    private CardColorEnum CardsColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
        int points = 0;
        for (int i = 0; i < 81 - 3; i++) {
            for(int j = 0; j < 81 - 3; j++) {
                if( cards[i][j].getColor() == CardsColor &&
                    cards[i+1][j+1].getColor() == CardsColor &&
                    cards[i+2][j+2].getColor() == CardsColor) {
                    points = points + pointsPerPattern;
                }
            }
        }
        return points;
    }

}
