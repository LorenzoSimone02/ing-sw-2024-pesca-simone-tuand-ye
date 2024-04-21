package it.polimi.ingsw.server.model.card.goldStrategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldStrategy;

public class CornerCount implements GoldStrategy {

    private int basePoints;

    public CornerCount(int basePoints) {
        this.basePoints = basePoints;
    }

    @Override
    public int calculatePoints(Card[][] cards, int xCoord, int yCoord) {
        int cornerCounter = 0;

        for (int i = -1; i <= 1; i = i+2) {

            for (int j = -1; j <= 1; j = j+2) {

                if (cards[xCoord+i][yCoord+j] != null) {
                    cornerCounter++;
                }
            }
        }

        return cornerCounter * basePoints;
    }
}
