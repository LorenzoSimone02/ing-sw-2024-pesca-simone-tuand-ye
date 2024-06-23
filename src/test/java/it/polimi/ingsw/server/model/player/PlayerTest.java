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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
    public void setCardTest(){
        testPlayer.setCard(goldCardArray.get(9), 41, 41);

        if (testPlayer.getCards()[41][41].equals(goldCardArray.get(9))) {
            if (testPlayer.getCardAt(41, 41).get().equals(goldCardArray.get(9))) {
                assertTrue(true);
            } else {
                fail();
            }

        } else {
            fail();

        }

    }

    @Test
    public void resourceAddTest() {
        testPlayer.addResource(new Resource(ResourceTypeEnum.FUNGI));

        if (testPlayer.getResources().contains(new Resource(ResourceTypeEnum.FUNGI))) {
            assertTrue(true);

        } else {
            fail();

        }

        testPlayer.removeResource(new Resource(ResourceTypeEnum.FUNGI));

        if (testPlayer.getResources().contains(new Resource(ResourceTypeEnum.FUNGI))) {
            fail();

        } else {
            assertTrue(true);

        }
    }

    @Test
    public void objectAddRemoveTest() {
        testPlayer.addObject(new Object(ObjectTypeEnum.INKWELL));

        if (testPlayer.getObjects().contains(new Object(ObjectTypeEnum.INKWELL))) {
            assertTrue(true);

        } else {
            fail();

        }

        testPlayer.removeObject(new Object(ObjectTypeEnum.INKWELL));

        if (testPlayer.getObjects().contains(new Object(ObjectTypeEnum.INKWELL))) {
            fail();

        } else {
            assertTrue(true);

        }


    }

    @Test
    public void usernameGetterTest(){
        if (testPlayer.getUsername().equals("testNick")) {
            assertTrue(true);

        } else {
            fail();

        }
    }

    @Test
    public void cardInHandAddRemoveTest(){
        testPlayer.addCardInHand(goldCardArray.get(9));
        if (testPlayer.getCardsInHand().contains(goldCardArray.get(9))) {
            assertTrue(true);

        } else {
            fail();

        }

        testPlayer.removeCardInHand(goldCardArray.get(9));
        if (testPlayer.getCardsInHand().contains(goldCardArray.get(9))) {
            fail();

        } else {
            assertTrue(true);

        }
    }

    @Test
    public void scoreTest() {
        testPlayer.setScore(12);

        if (testPlayer.getScore() == 12) {
            assertTrue(true);

        } else {
            fail();

        }
    }

    @Test
    public void tokenTest() {
        testPlayer.setToken(new PlayerToken(TokenColorEnum.RED));

        if (testPlayer.getToken().getColor().equals(TokenColorEnum.RED)) {
            assertTrue(true);

        } else {
            fail();

        }
    }

    @Test
    public void setStarterCardTest() {
        testPlayer.setStarterCard(starterCardArray.get(3));

        if (testPlayer.getStarterCard().equals(starterCardArray.get(3))) {
            assertTrue(true);

        } else {
            fail();

        }
    }

    @Test
    public void setObjectiveCardTest() {
        testPlayer.setObjectiveCard(objectiveCardArray.get(3));

        if (testPlayer.getObjectiveCard().equals(objectiveCardArray.get(3))) {
            assertTrue(true);

        } else {
            fail();

        }
    }

    @Test
    public void gameGetterTest() {

        if (testPlayer.getGame().getInfo().getId() == 999) {
            assertTrue(true);

        } else {
            fail();

        }
    }
}
