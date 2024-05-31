package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.objectives.ObjectiveType;
import it.polimi.ingsw.server.model.player.Player;

public class ObjectiveCard extends Card {

    private final ObjectiveType objectiveType;
    private final String objectiveDescription;

    public ObjectiveCard(String jsonData) {
        Gson gson = new Gson();
        ObjectiveCard proprieties = gson.fromJson(jsonData, ObjectiveCard.class);
        this.id = proprieties.getId();
        this.color = proprieties.getColor();
        this.face = proprieties.getFace();
        this.objectiveType = proprieties.getObjectiveType();
        this.objectiveDescription = proprieties.getObjectiveDescription();
    }

    public ObjectiveType getObjectiveType() {
        return objectiveType;
    }

    public String getObjectiveDescription() {
        return objectiveDescription;
    }

    public int calculatePoints(Player player) {
        return objectiveType.getStrategy().calculatePoints(player);
    }
}
