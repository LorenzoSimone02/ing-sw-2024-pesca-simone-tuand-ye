package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.objectives.Objective;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class ObjectiveCard extends Card{
    private Objective objective;

    public ObjectiveCard(File jsonFile) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            Properties data = gson.fromJson(reader, Properties.class);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Objective getObjective() {
        return objective;
    }
}
