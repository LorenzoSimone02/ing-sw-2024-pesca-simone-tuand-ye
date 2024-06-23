package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GoldStrategyTest {

    private Game testGame;
    private Player testPlayer;

    private ArrayList<GoldCard> goldCardArray;
    private List<Object> playerResources;
    private Card[][] placedCards;

    @BeforeEach
    void setUp() throws IOException {
        placedCards = new ResourceCard[81][81];
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

        testGame = new Game(40320);
        testPlayer = new Player("testNick", testGame);

        testPlayer.setCard(goldCardArray.get(1), 39, 39);
        testPlayer.setCard(goldCardArray.get(2), 41, 41);
        testPlayer.addObject(new Object(ObjectTypeEnum.INKWELL));
        testPlayer.addObject(new Object(ObjectTypeEnum.INKWELL));

    }

    @Test
    public void strategyGetterTest() {
        GoldStrategyType goldStrategyTestType = GoldStrategyType.OBJECT_COUNT_INKWELL;

        GoldStrategy strategyResult = goldStrategyTestType.getStrategy();

        if (strategyResult instanceof ObjectCount) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test
    public void cornerCountTest() {
        GoldStrategy cornerCount = new CornerCount(2);
        int cornerCountResult = cornerCount.calculatePoints(testPlayer, 40, 40);

        if (cornerCountResult == 4) {
            assertTrue(true);

        } else {
            fail();
        }
    }

    @Test
    public void plainPointsTest() {
        GoldStrategy plainPoints = new PlainPoints(5);
        int plainPointsResult = plainPoints.calculatePoints(testPlayer, 40, 40);

        if (plainPointsResult == 5) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test
    public void objectCountTest() {
        GoldStrategy objectCount = new ObjectCount(1, ObjectTypeEnum.INKWELL);
        int objectCountResult = objectCount.calculatePoints(testPlayer, 40, 40);

        if (objectCountResult == 2) {
            assertTrue(true);
        } else {
            fail();
        }
    }
}
