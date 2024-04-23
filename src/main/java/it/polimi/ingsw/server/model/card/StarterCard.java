package it.polimi.ingsw.server.model.card;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class StarterCard extends ResourceCard {

    public StarterCard(File jsonFile) {
        super(jsonFile);
        this.corners.clear();
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            StarterCard proprieties = gson.fromJson(reader, StarterCard.class);
            this.corners.addAll(proprieties.getCorners());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
