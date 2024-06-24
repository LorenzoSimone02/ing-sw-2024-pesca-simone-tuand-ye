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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceCardTest {

    final ArrayList<ResourceCard> resCardArray = new ArrayList<>(40);
    final ArrayList<GoldCard> goldCardArray = new ArrayList<>(40);
    final ArrayList<StarterCard> starterCardArray = new ArrayList<>(6);
    final ArrayList<ObjectiveCard> objectiveArray = new ArrayList<>(16);

    @BeforeEach
    void setUp() throws IOException {
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
        for (int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/goldcards/goldCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            GoldCard card = new GoldCard(jsonData);
            goldCardArray.add(card);
        }
        for (int i = 1; i <= 6; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/startercards/starterCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            StarterCard card = new StarterCard(jsonData);
            starterCardArray.add(card);
        }
        for (int i = 1; i <= 16; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveArray.add(card);
        }
    }

    @Test
    @DisplayName("Validate that all corners and their attributes in resourceCards are not null")
    public void validateNotNullAllResourceCards() {

        for (ResourceCard currCard : resCardArray) {
            assertNotNull(currCard);
            assertNotNull(currCard.getFace());
            assertNotNull(currCard.getBackResources());
            assertNotNull(currCard.getColor());
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
