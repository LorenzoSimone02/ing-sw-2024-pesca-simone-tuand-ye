package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.player.Player;

public interface GoldStrategy {

    int calculatePoints(Player player, int xCoord, int yCoord);
}
