package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldStrategy;
import it.polimi.ingsw.server.model.player.Player;

public class CornerCount implements GoldStrategy {

    private final int basePoints;

    public CornerCount(int basePoints) {
        this.basePoints = basePoints;
    }

    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        int cornerCounter = 0;
        Card[][] cards = player.getCards();

        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (cards[xCoord + i][yCoord + j] != null) {
                    cornerCounter++;
                }
            }
        }
        return cornerCounter * basePoints;
    }
}
