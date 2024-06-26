package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.Deck;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.AfterEach;
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
    static int id = 5;

    @BeforeEach
    void setUp() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("Server", 1099 + id, 5001);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        gameController.addPlayer("p1");

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

        Player p = gameController.getPlayerController("p1").getPlayer();
        p.setToken(new PlayerToken(TokenColorEnum.YELLOW));
        p.setStarterCard(starterCardArray.get(1));
        p.setObjectiveCard(objectiveCardArray.get(1));

        gameController.startGame();

        id++;
    }

    @AfterEach
    void tearDown() {
        serverNetworkHandler.stop();
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

        assertEquals(GameStatusEnum.PLAYING, GameStatusEnum.valueOf(gameSave.getGameStatus()));

    }

    @Test
    void getResourceDeck() {

        Deck testDeck = new Deck();
        for(int i = 0; i < 40; i++){
            testDeck.addCard(resourceCardArray.get(i));
        }
        gameController.getGame().getTable().setResourceDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(40, gameSave.getResourceDeck().size());
        for(int i = 0; i < 40; i++){
            assertEquals(resourceCardArray.get(i).getId(), gameSave.getResourceDeck().get(i).getId());
        }
    }

    @Test
    void getGoldDeck() {

        Deck testDeck = new Deck();
        for(int i = 0; i < 40; i++){
            testDeck.addCard(resourceCardArray.get(i));
        }
        gameController.getGame().getTable().setGoldDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(40, gameSave.getGoldDeck().size());
        for(int i = 0; i < 40; i++){
            assertEquals(resourceCardArray.get(i).getId(), gameSave.getGoldDeck().get(i).getId());
        }

    }

    @Test
    void getObjectiveDeck() {

        Deck testDeck = new Deck();
        for(int i = 0; i < 16; i++){
            testDeck.addCard(objectiveCardArray.get(i));
        }
        gameController.getGame().getTable().setObjectiveDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(16, gameSave.getObjectiveDeck().size());
        for (int i = 0; i < 16; i++) {
            assertEquals(objectiveCardArray.get(i).getId(), gameSave.getObjectiveDeck().get(i).getId());
        }

    }

    @Test
    void getStarterDeck() {

        Deck testDeck = new Deck();
        for(int i = 0; i < 6; i++){
            testDeck.addCard(starterCardArray.get(i));
        }
        gameController.getGame().getTable().setStarterDeck(testDeck);

        GameSave gameSave = new GameSave(gameController.getGame());
        assertEquals(6, gameSave.getStarterDeck().size());
        for (int i = 0; i < 6; i++) {
            assertEquals(starterCardArray.get(i).getId(), gameSave.getStarterDeck().get(i).getId());
        }

    }

    @Test
    void getObjectiveCards() {

        gameController.getGame().getTable().getObjectiveCards().clear();
        gameController.getGame().getTable().addObjectiveCard(objectiveCardArray.getFirst());
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(objectiveCardArray.getFirst().getId(), gameSave.getObjectiveCards().getFirst().getId());

    }

    @Test
    void getCardsOnGround() {

        gameController.getGame().getTable().getCardsOnGround().clear();
        gameController.getGame().getTable().addCardOnGround(resourceCardArray.getFirst());
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(resourceCardArray.getFirst().getId(), gameSave.getCardsOnGround().getFirst().getId());

    }

    @Test
    void getPlayerSaves() {
        GameSave gameSave = new GameSave(gameController.getGame());

        assertEquals(1, gameSave.getPlayerSaves().size());
        for (int i = 0; i < gameSave.getPlayerSaves().size(); i++) {
            assertNotNull(gameSave.getPlayerSaves().get(i));
            assertEquals(gameController.getPlayerController("p" + (i + 1)).getPlayer().getUsername(), gameSave.getPlayerSaves().get(i).getUsername());
        }

    }

}