package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
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

class PlayerSaveTest {

    private ServerNetworkHandler serverNetworkHandler;
    private GameController gameController;
    private ArrayList<ResourceCard> resourceCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ObjectiveCard> objectiveCardArray;

    @BeforeEach
    void setUp() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("Server", 1099, 5001);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        gameController.addPlayer("p1");

        gameController.startGame();

        resourceCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();
        objectiveCardArray = new ArrayList<>();

        for(int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ResourceCard card = new ResourceCard(jsonData);
            resourceCardArray.add(card);
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
    void getUsername() {

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertNotNull(playerSave.getUsername());
        assertEquals("p1", playerSave.getUsername());

    }

    @Test
    void getScore() {

        gameController.getPlayerController("p1").getPlayer().setScore(69);
        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertEquals(69, playerSave.getScore());

    }

    @Test
    void getTokenColor() {

        gameController.getPlayerController("p1").getPlayer().setToken(new PlayerToken(TokenColorEnum.YELLOW));
        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertNotNull(playerSave.getTokenColor());
        assertEquals(TokenColorEnum.YELLOW, playerSave.getTokenColor());

    }

    @Test
    void getCards() {

        gameController.getPlayerController("p1").getPlayer().setStarterCard(starterCardArray.get(1));
        gameController.getPlayerController("p1").placeCard(resourceCardArray.get(1), 1, 1);
        gameController.getPlayerController("p1").placeCard(resourceCardArray.get(2), 2, 2);

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertNotNull(playerSave.getStarterCard());
        assertEquals(starterCardArray.get(1), playerSave.getStarterCard());
        assertEquals(resourceCardArray.get(1), playerSave.getCards()[1][1]);
        assertEquals(resourceCardArray.get(2), playerSave.getCards()[2][2]);

    }

    @Test
    void getOrderedCards() {

        gameController.getPlayerController("p1").getPlayer().addCardInHand(resourceCardArray.get(1));
        gameController.getPlayerController("p1").getPlayer().addCardInHand(resourceCardArray.get(2));
        gameController.getPlayerController("p1").getPlayer().addCardInHand(resourceCardArray.get(3));

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertNotNull(playerSave.getOrderedCards());
        assertEquals(3, playerSave.getOrderedCards().size());

        for(int i = 0; i < gameController.getPlayerController("p1").getPlayer().getCardsInHand().size(); i++) {
            assertEquals(gameController.getPlayerController("p1").getPlayer().getCardsInHand().get(i), playerSave.getOrderedCards().get(i));
        }

    }

    @Test
    void getObjectiveCard() {

        gameController.getPlayerController("p1").getPlayer().setObjectiveCard(objectiveCardArray.get(5));

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertNotNull(playerSave.getObjectiveCard());
        assertEquals(objectiveCardArray.get(5), playerSave.getObjectiveCard());

    }

    @Test
    void getResourcesAndObjects() {

        gameController.getPlayerController("p1").getPlayer().addResource(new Resource(ResourceTypeEnum.FUNGI));
        gameController.getPlayerController("p1").getPlayer().addResource(new Resource(ResourceTypeEnum.PLANT));
        gameController.getPlayerController("p1").getPlayer().addObject(new Object(ObjectTypeEnum.INKWELL));
        gameController.getPlayerController("p1").getPlayer().addObject(new Object(ObjectTypeEnum.QUILL));

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertFalse(playerSave.getResourcesAndObjects().isEmpty());

        int numOfFungi = 0;
        int numOfPlant = 0;
        int numOfInkwell = 0;
        int numOfQuill = 0;

        for(Resource resource : gameController.getPlayerController("p1").getPlayer().getResources()) {
            if(resource.getType().equals(ResourceTypeEnum.FUNGI)) {
                numOfFungi++;
            } else if(resource.getType().equals(ResourceTypeEnum.PLANT)) {
                numOfPlant++;
            }
        }

        for(Object object : gameController.getPlayerController("p1").getPlayer().getObjects()) {
            if(object.getType().equals(ObjectTypeEnum.INKWELL)) {
                numOfInkwell++;
            } else if(object.getType().equals(ObjectTypeEnum.QUILL)) {
                numOfQuill++;
            }
        }

        assertEquals(1, numOfFungi);
        assertEquals(1, numOfPlant);
        assertEquals(1, numOfInkwell);
        assertEquals(1, numOfQuill);

    }

    @Test
    void isFirstPlayer() {

        gameController.getPlayerController("p1").getPlayer().getGame().getInfo().setFirstPlayer(gameController.getPlayerController("p1").getPlayer());

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertTrue(playerSave.isFirstPlayer());

    }

    @Test
    void isActive() {

        gameController.getPlayerController("p1").getPlayer().getGame().getInfo().setActivePlayer(gameController.getPlayerController("p1").getPlayer());

        PlayerSave playerSave = new PlayerSave(gameController.getPlayerController("p1").getPlayer());

        assertNotNull(playerSave);
        assertTrue(playerSave.isActive());

    }
}