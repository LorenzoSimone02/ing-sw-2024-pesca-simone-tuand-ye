package it.polimi.ingsw.server.model.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    ResourceCard card;

    @BeforeEach
    void setUp() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard1.json"))));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String jsonData = stringBuilder.toString();
        card = new ResourceCard(jsonData);
    }

    @Test
    void getId() {
        assertEquals(1, card.getId());
    }

    @Test
    void getColor() {
        assertEquals(CardColorEnum.RED, card.getColor());
    }

    @Test
    void getFace() {
        assertEquals(FaceEnum.FRONT, card.getFace());
    }

    @Test
    void setFace() {
        card.setFace(FaceEnum.BACK);
        assertEquals(FaceEnum.BACK, card.getFace());
    }
}