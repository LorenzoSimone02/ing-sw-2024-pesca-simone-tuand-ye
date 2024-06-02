package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ResourceCardTest {

    final ArrayList<ResourceCard> resCardArray = new ArrayList<>(40);

    @BeforeEach
    void setUp() {
        try {
            for (int i = 1; i <= 40; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonData = stringBuilder.toString();
                ResourceCard card = new ResourceCard(jsonData);
                resCardArray.add(card);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Validate that all corners and their attributes in resourceCards are not null")
    public void validateNotNullAllResourceCards() {

        for (ResourceCard currCard : resCardArray) {
            if (currCard == null) {
                fail("resCard is null");
            }
            //null attributes tests
            if (currCard.getFace() == null) {
                fail("resCard face is null: card " + resCardArray.indexOf(currCard));
            }
            if (currCard.getBackResources() == null) {
                fail("back resource is null: card " + resCardArray.indexOf(currCard));
            }
            if (currCard.getColor() == null) {
                fail("card color is null: card " + resCardArray.indexOf(currCard));
            }
        }
    }

    @Test
    void getPoints() {
        ResourceCard testCard = resCardArray.getFirst();
        assertEquals(0, testCard.getPoints());
    }

    @Test
    void getCorners() {
        ResourceCard testCard = resCardArray.getFirst();
        assertEquals(8, testCard.getCorners().size());
        assertEquals(4, testCard.getCorners().stream().filter(corner -> corner.getFace().equals(FaceEnum.BACK)).count());
        assertEquals(4, testCard.getCorners().stream().filter(corner -> corner.getFace().equals(FaceEnum.FRONT)).count());
    }

    @Test
    void getCorner() {
        ResourceCard testCard = resCardArray.getFirst();
        assertEquals(testCard.getCorner(CornerLocationEnum.TOP_LEFT).getLocation(), CornerLocationEnum.TOP_LEFT);
    }

    @Test
    void getBackResources() {
        ResourceCard testCard = resCardArray.getFirst();
        assertEquals(1, testCard.getBackResources().size());
        assertEquals(ResourceTypeEnum.FUNGI, testCard.getBackResources().getFirst().getType());
    }
}
