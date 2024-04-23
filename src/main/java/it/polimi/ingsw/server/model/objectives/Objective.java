package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.player.Player;

public record Objective(ObjectiveType type) {

    public ObjectiveType getType() {
        return type;
    }

    public int getPoints(Player player) {
        return type.getStrategy().calculatePoints(player);
    }
}
