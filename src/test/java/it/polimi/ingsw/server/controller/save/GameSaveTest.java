package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.Deck;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GameSaveTest {

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
    void getMaxPlayers() {

        gameController.setMaxPlayers(4);
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(4, gameSave.getMaxPlayers());

    }

    @Test
    void getFirstPlayer() {

        gameController.getGame().getInfo().setFirstPlayer(gameController.getPlayerController("p1").getPlayer());
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals("p1", gameSave.getFirstPlayer());

    }

    @Test
    void getActivePlayer() {

        gameController.getGame().getInfo().setActivePlayer(gameController.getPlayerController("p1").getPlayer());
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals("p1", gameSave.getActivePlayer());

    }

    @Test
    void getGameStatus() {

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(GameStatusEnum.PLAYING, gameSave.getGameStatus());

    }

    @Test
    void getResourceDeck() {

        Deck testDeck = new Deck();
        gameController.getGame().getTable().setResourceDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(testDeck, gameSave.getResourceDeck());

    }

    @Test
    void getGoldDeck() {

        Deck testDeck = new Deck();
        gameController.getGame().getTable().setGoldDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(testDeck, gameSave.getGoldDeck());

    }

    @Test
    void getObjectiveDeck() {

        Deck testDeck = new Deck();
        gameController.getGame().getTable().setObjectiveDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(testDeck, gameSave.getObjectiveDeck());

    }

    @Test
    void getStarterDeck() {

        Deck testDeck = new Deck();
        gameController.getGame().getTable().setStarterDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(testDeck, gameSave.getStarterDeck());

    }

    @Test
    void getObjectiveCards() {

        gameController.getGame().getTable().addObjectiveCard(objectiveCardArray.get(0));
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(objectiveCardArray.get(0), gameSave.getObjectiveCards());

    }

    @Test
    void getCardsOnGround() {

        gameController.getGame().getTable().addCardOnGround(resourceCardArray.get(0));
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(resourceCardArray.get(0), gameSave.getCardsOnGround());

    }

    @Test
    void getPlayerSaves() {

        gameController.addPlayer("p2");
        gameController.addPlayer("p3");
        gameController.addPlayer("p4");

        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(4, gameSave.getPlayerSaves().size());
        for (int i = 0; i < gameSave.getPlayerSaves().size(); i++) {
            assertNotNull(gameSave.getPlayerSaves().get(i));
            assertEquals(gameController.getPlayerController("p" + (i + 1)).getPlayer(), gameSave.getPlayerSaves().get(i));
        }

    }
    
}