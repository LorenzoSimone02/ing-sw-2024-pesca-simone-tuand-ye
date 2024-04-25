package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.objectives.ObjectiveType;
import it.polimi.ingsw.server.model.player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ObjectiveCard extends Card {

    private final ObjectiveType objectiveType;

    public ObjectiveCard(File jsonFile) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            ObjectiveCard proprieties = gson.fromJson(reader, ObjectiveCard.class);
            this.id = proprieties.getId();
            this.color = proprieties.getColor();
            this.face = proprieties.getFace();
            this.objectiveType = proprieties.getObjectiveType();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectiveType getObjectiveType() {
        return objectiveType;
    }

    public int calculatePoints(Player player) {
        return objectiveType.getStrategy().calculatePoints(player);
    }
}
