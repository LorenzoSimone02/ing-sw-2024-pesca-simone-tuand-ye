package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.objectives.strategies.BottomLeftLShape;
import it.polimi.ingsw.server.model.objectives.strategies.ObjectStrategy;
import it.polimi.ingsw.server.model.objectives.strategies.ResourceStrategy;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectiveStrategyTest {

    private Player testPlayer;
    private Game testGame;
    private ArrayList<GoldCard> goldCardArray;
    private ArrayList<ResourceCard> resCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ObjectiveCard> objectiveCardArray;

    @BeforeEach
    public void setUp() throws IOException {

        goldCardArray = new ArrayList<>();
        resCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();
        objectiveCardArray = new ArrayList<>();

        testGame = new Game(999);
        testPlayer = new Player("testNick", testGame);

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
        for(int i = 1; i <= 6; i++) {
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
        for(int i = 1; i <= 16; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveCardArray.add(card);
        }
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

        testPlayer.setStarterCard(starterCardArray.get(1));
        testPlayer.setCard(starterCardArray.get(1), 40, 40);

        //BLLShape
        testPlayer.setCard(resCardArray.get(12), 41 ,39);
        testPlayer.setCard(resCardArray.get(10), 39, 39);
        testPlayer.setCard(resCardArray.get(32), 42, 38);

        //BRLShape
        testPlayer.setCard(resCardArray.get(7), 40, 38);
        testPlayer.setCard(resCardArray.get(8), 38, 38);

        //TLLShape
        testPlayer.setCard(resCardArray.get(34), 42, 36);
        testPlayer.setCard(resCardArray.get(35), 43, 37);
        testPlayer.setCard(resCardArray.get(27), 40, 36);

        //TRLShape
        testPlayer.setCard(resCardArray.get(0), 37, 37);
        testPlayer.setCard(resCardArray.get(29), 38, 36);


        //TLDiagonal
        testPlayer.setCard(resCardArray.get(38), 41, 37);
        testPlayer.setCard(resCardArray.get(37), 43, 39);

        //TRDiagonal
        testPlayer.setCard(resCardArray.get(1), 36, 38);
        testPlayer.setCard(resCardArray.get(4), 35, 39);

        //Resource/Object population
        for (int i = 0; i < 10; i++) {
            testPlayer.addResource(new Resource(ResourceTypeEnum.FUNGI));
        }
        for (int i = 0; i < 7; i++) {
            testPlayer.addObject(new Object(ObjectTypeEnum.INKWELL));
        }
        for (int i = 0; i < 18; i++) {
            testPlayer.addObject(new Object(ObjectTypeEnum.QUILL));
        }
        for (int i = 0; i < 23; i++) {
            testPlayer.addObject(new Object(ObjectTypeEnum.MANUSCRIPT));
        }
    }

    @Test
    public void BottomLeftLShapeTest() {
        ObjectiveStrategy BottomLeftLShapeStrategy = ObjectiveType.BOTTOM_LEFT_L_SHAPE.getStrategy();

        int result = BottomLeftLShapeStrategy.calculatePoints(testPlayer);

        assertEquals(3, result);

    }

    @Test
    public void BottomRightLShapeTest() {
        ObjectiveStrategy BottomRightLShapeStrategy = ObjectiveType.BOTTOM_RIGHT_L_SHAPE.getStrategy();

        int result = BottomRightLShapeStrategy.calculatePoints(testPlayer);

        assertEquals(3, result);

    }

    @Test
    public void TopLeftLShapeTest() {
        ObjectiveStrategy TopLeftLShapeStrategy = ObjectiveType.TOP_LEFT_L_SHAPE.getStrategy();

        int result = TopLeftLShapeStrategy.calculatePoints(testPlayer);

        assertEquals(3, result);

    }
    @Test
    public void TopRightLShapeTest() {
        ObjectiveStrategy TopRightLShapeStrategy = ObjectiveType.TOP_RIGHT_L_SHAPE.getStrategy();

        int result = TopRightLShapeStrategy.calculatePoints(testPlayer);

        assertEquals(3, result);

    }
    @Test
    public void TopLeftDiagonalTest() {
        ObjectiveStrategy TopLeftDiagonalStrategy = ObjectiveType.TOP_LEFT_DIAGONAL_PURPLE.getStrategy();

        int result = TopLeftDiagonalStrategy.calculatePoints(testPlayer);

        assertEquals(2, result);

    }
    @Test
    public void TopRightDiagonalTest() {
        ObjectiveStrategy TopRightDiagonalStrategy = ObjectiveType.TOP_RIGHT_DIAGONAL_RED.getStrategy();
        int result = TopRightDiagonalStrategy.calculatePoints(testPlayer);

        assertEquals(2, result);

    }

    @Test
    public void ResourceStrategyTest() {
        ObjectiveStrategy ResourceStrategyTest = ObjectiveType.RESOURCE_FUNGI.getStrategy();
        int result = ResourceStrategyTest.calculatePoints(testPlayer);

        assertEquals(6, result);
    }

    @Test
    public void ObjectStrategyTest() {
        ObjectiveStrategy ObjectStrategyTest = ObjectiveType.OBJECT_INKWELL.getStrategy();
        int result = ObjectStrategyTest.calculatePoints(testPlayer);

        assertEquals(6, result);

        ObjectStrategyTest = ObjectiveType.OBJECT_QUILL_INKWELL_MANUSCRIPT.getStrategy();
        result = ObjectStrategyTest.calculatePoints(testPlayer);

        assertEquals(21, result);

    }

    @Test
    public void ObjectiveCardPointsTest() {
        ObjectiveCard objectiveCardTest = objectiveCardArray.get(0);
        int result = objectiveCardTest.calculatePoints(testPlayer);

        assertEquals(2, result);

    }

}
