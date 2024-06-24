package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.card.goldstrategies.GoldStrategyType;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoldCardTest {

    private ArrayList<GoldCard> goldCardArray;
    private List<Resource> playerResources;

    @BeforeEach
    void setUp() throws IOException {
        goldCardArray = new ArrayList<>();
        playerResources = new ArrayList<>();
        for(int i = 1; i <= 40; i++) {
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

        playerResources.add(new Resource(ResourceTypeEnum.FUNGI));
        playerResources.add(new Resource(ResourceTypeEnum.FUNGI));
        playerResources.add(new Resource(ResourceTypeEnum.FUNGI));

    }

    @Test
    public void meetRequirementsTrueTest() {

        GoldCard testCard = goldCardArray.get(8);
        boolean result = testCard.meetRequirements(playerResources);

        assertTrue(result);

    }

    @Test
    public void meetRequirementsFalseTest() {

        GoldCard testCard = goldCardArray.get(4);
        boolean result = testCard.meetRequirements(playerResources);

        assertFalse(result);

    }

    @Test
    public void getPointsStrategyTest() {
        GoldCard testCard = goldCardArray.get(9);
        assertEquals(GoldStrategyType.PLAIN_POINTS_5, testCard.getPointsStrategy());

        GoldCard testCard2 = goldCardArray.get(20);
        assertEquals(GoldStrategyType.OBJECT_COUNT_INKWELL, testCard2.getPointsStrategy());
    }

}
