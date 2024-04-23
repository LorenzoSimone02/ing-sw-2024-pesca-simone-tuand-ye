package it.polimi.ingsw.server.model.objectives.strategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.ObjectiveStrategy;

public class TopRightDiagonal implements ObjectiveStrategy {

    private CardColorEnum CardsColor;
    int pointsPerPattern;

    public int calculatePoints(Card[][] cards) {
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
                    rowIndex = (i - j) - 1;
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
                    rowIndex = (len - 1) - j;
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
}
