package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.*;


import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.ObjectName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player testPlayer;
    private Game testGame;
    private ArrayList<GoldCard> goldCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ObjectiveCard> objectiveCardArray;

    @BeforeEach
    public void setUp() throws IOException {

        goldCardArray = new ArrayList<>();
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
    }

    @Test
    public void setAndGetCardTest(){

        testPlayer.setCard(goldCardArray.get(9), 41, 41);

        assertEquals(goldCardArray.get(9), testPlayer.getCards()[41][41]);
        assertEquals(goldCardArray.get(9), testPlayer.getCardAt(41, 41).get());

    }

    @Test
    public void resourceAddAndRemoveTest() {

        testPlayer.addResource(new Resource(ResourceTypeEnum.FUNGI));
        assertTrue(testPlayer.getResources().contains(new Resource(ResourceTypeEnum.FUNGI)));

        testPlayer.removeResource(new Resource(ResourceTypeEnum.FUNGI));
        assertFalse(testPlayer.getResources().contains(new Resource(ResourceTypeEnum.FUNGI)));

    }

    @Test
    public void objectAddAndRemoveTest() {

        testPlayer.addObject(new Object(ObjectTypeEnum.INKWELL));
        assertTrue(testPlayer.getObjects().contains(new Object(ObjectTypeEnum.INKWELL)));

        testPlayer.removeObject(new Object(ObjectTypeEnum.INKWELL));
        assertFalse(testPlayer.getObjects().contains(new Object(ObjectTypeEnum.INKWELL)));

    }

    @Test
    public void usernameGetterTest(){

        assertEquals("testNick", testPlayer.getUsername());

    }

    @Test
    public void cardInHandAddAndRemoveTest(){

        testPlayer.addCardInHand(goldCardArray.get(9));
        assertTrue(testPlayer.getCardsInHand().contains(goldCardArray.get(9)));

        testPlayer.removeCardInHand(goldCardArray.get(9));
        assertFalse(testPlayer.getCardsInHand().contains(goldCardArray.get(9)));

    }

    @Test
    public void scoreTest() {

        testPlayer.setScore(12);
        assertEquals(12, testPlayer.getScore());

    }

    @Test
    public void tokenTest() {

        testPlayer.setToken(new PlayerToken(TokenColorEnum.RED));
        assertEquals(TokenColorEnum.RED, testPlayer.getToken().getColor());

    }

    @Test
    public void setStarterCardTest() {

        testPlayer.setStarterCard(starterCardArray.get(3));
        assertEquals(starterCardArray.get(3), testPlayer.getStarterCard());

    }

    @Test
    public void setObjectiveCardTest() {

        testPlayer.setObjectiveCard(objectiveCardArray.get(3));
        assertEquals(objectiveCardArray.get(3), testPlayer.getObjectiveCard());

    }

    @Test
    public void gameGetterTest() {

        assertEquals(999, testPlayer.getGame().getInfo().getId());

    }
}
