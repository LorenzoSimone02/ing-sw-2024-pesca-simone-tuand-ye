package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.objectives.Objective;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ObjectiveCard extends Card {

    private final Objective objective;

    public ObjectiveCard(File jsonFile) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            ObjectiveCard proprieties = gson.fromJson(reader, ObjectiveCard.class);
            this.id = proprieties.getId();
            this.color = proprieties.getColor();
            this.face = proprieties.getFace();
            this.objective = proprieties.getObjective();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Objective getObjective() {
        return objective;
    }
}
