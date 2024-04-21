package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.player.Player;

public interface GoldStrategy {

    int calculatePoints(Player player, int xCoord, int yCoord);
}
