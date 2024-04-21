package it.polimi.ingsw.server.model.card.goldStrategies;

import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldStrategy;

public class PlainPoints implements GoldStrategy {
    private int basePoints;

    public PlainPoints(int basePoints) {
        this.basePoints = basePoints;
    }

    @Override
    public int calculatePoints(Player player, int xCoord, int yCoord) {
        return basePoints;
    }
}
