package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.player.Player;

public interface ObjectiveStrategy {

    int calculatePoints(Player player);
}
