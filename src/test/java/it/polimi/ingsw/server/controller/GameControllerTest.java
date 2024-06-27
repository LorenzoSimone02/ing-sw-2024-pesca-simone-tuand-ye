package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private ServerNetworkHandler serverNetworkHandler;
    private GameController gameController;
    private ArrayList<GoldCard> goldCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ResourceCard> resourceCardArray;
    private static int id = 50;

    @BeforeEach
    void setup() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1099 + id, 5001);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        resourceCardArray = new ArrayList<>();
        goldCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();

        Deck resourceDeck = new Deck();
        for (int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ResourceCard card = new ResourceCard(jsonData);
            resourceCardArray.add(card);
            resourceDeck.addCard(card);
        }
        gameController.getGame().getTable().setResourceDeck(resourceDeck);

        Deck goldDeck = new Deck();
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
            goldDeck.addCard(card);
        }
        gameController.getGame().getTable().setGoldDeck(goldDeck);
        Deck starterDeck = new Deck();
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
            starterDeck.addCard(card);
        }
        gameController.getGame().getTable().setStarterDeck(starterDeck);
        Deck objectiveDeck = new Deck();
        for (int i = 1; i <= 16; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveDeck.addCard(card);
        }
        gameController.getGame().getTable().setObjectiveDeck(objectiveDeck);
        id++;
    }

    @AfterEach
    void tearDown() {
        serverNetworkHandler.stop();
    }

    @Test
    @DisplayName("Test valid game id")
    public void validGameId() {
        assertEquals(1, gameController.getGame().getInfo().getId());
    }

    @Test
    @DisplayName("Test card given ID")
    public void validCardGivenID() {

        ResourceCard chosenCard = resourceCardArray.getFirst();
        assertEquals(chosenCard.getId(), resourceCardArray.getFirst().getId());
    }

    @Test
    @DisplayName("Test valid network handler")
    public void validNetworkHandler() {

        assertNotNull(gameController.getNetworkHandler());
        assertEquals(serverNetworkHandler, gameController.getNetworkHandler());
    }

    @Test
    @DisplayName("Test valid player controllers")
    public void validPlayerControllers() {

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");
        gameController.addPlayer("p3");
        gameController.addPlayer("p4");

        assertEquals(4, gameController.getPlayerControllers().size());
        assertNotNull(gameController.getPlayerController(gameController.getPlayerByNick("p1").orElse(null)));
        assertNotNull(gameController.getPlayerController(gameController.getPlayerByNick("p2").orElse(null)));
        assertNotNull(gameController.getPlayerController("p3"));
        assertNotNull(gameController.getPlayerController("p4"));

        gameController.removePlayer(gameController.getPlayerByNick("p1").orElse(null));
        assertFalse(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertEquals(3, gameController.getPlayerControllers().size());

    }

    @Test
    @DisplayName("Test valid player number")
    public void validPlayerNumber() {

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");

        assertEquals(2, gameController.getGame().getPlayers().size());
        assertEquals(0, gameController.getGame().getOfflinePlayers().size());
    }

    @Test
    @DisplayName("Test valid players management")
    public void validPlayerList() {

        for (int i = 0; i < 4; i++) {
            gameController.addPlayer("p" + i);
            if (gameController.getGame().getInfo().getPlayersNumber() != i + 1) {
                fail("Tried to add a player but the number of players is not correct");
            }
            if (!gameController.getGame().getPlayers().get(i).getUsername().equals("p" + i)) {
                fail("Wrong player added");
            }
            assertEquals(gameController.getGame().getPlayers().get(i), gameController.getPlayerByNick("p" + i).orElse(null));
        }

        gameController.addPlayer("p0");
        gameController.addPlayer("p4");

        gameController.removePlayer(gameController.getPlayerByNick("p2").orElse(null));

        for (int i = 0; i < gameController.getGame().getInfo().getPlayersNumber(); i++) {
            assertNotEquals("p2", gameController.getGame().getPlayers().get(i).getUsername());
        }
        assertEquals(3, gameController.getGame().getInfo().getPlayersNumber());

        gameController.addPlayer("p2");
        assertEquals(4, gameController.getGame().getInfo().getPlayersNumber());
        assertTrue(gameController.getGame().getPlayers().contains(gameController.getPlayerByNick("p2").orElse(null)));

    }

    @Test
    @DisplayName("Test valid game status")
    public void validGameStatus() {

        assertEquals(GameStatusEnum.WAITING_FOR_PLAYERS, gameController.getGame().getInfo().getGameStatus());

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");

        gameController.startGame();
        assertEquals(gameController.getGame().getInfo().getGameStatus(), GameStatusEnum.CHOOSING_COLOR);

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_STARTER_FACE);
        assertEquals(GameStatusEnum.CHOOSING_STARTER_FACE, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);
        assertEquals(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.LAST_TURN);
        assertEquals(GameStatusEnum.LAST_TURN, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        assertEquals(GameStatusEnum.PLAYING, gameController.getGame().getInfo().getGameStatus());
    }

    @Test
    @DisplayName("Test valid initial player in hand cards")
    public void validInitialPlayerCardsInHand() {

        int numOfGoldCards = 0;
        int numOfResourceCards = 0;

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");

        gameController.startGame();

        for (Player player : gameController.getGame().getPlayers()) {
            for (int i = 0; i < player.getCardsInHand().size(); i++) {
                if (player.getCardsInHand().get(i) instanceof GoldCard) {
                    numOfGoldCards++;
                } else if (player.getCardsInHand().get(i) instanceof ResourceCard) {
                    numOfResourceCards++;
                }
            }
        }

        assertEquals(2, numOfGoldCards);
        assertEquals(4, numOfResourceCards);

    }

    @Test
    @DisplayName("Test valid first player + turn management")
    public void validFirstPlayer() {

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");
        Player p1 = gameController.getPlayerByNick("p1").orElse(null);
        Player p2 = gameController.getPlayerByNick("p2").orElse(null);
        PlayerController p1Controller = gameController.getPlayerController("p1");
        PlayerController p2Controller = gameController.getPlayerController("p2");

        p1Controller.chooseToken(TokenColorEnum.RED);
        p2Controller.chooseToken(TokenColorEnum.BLUE);
        p1Controller.setStarterCard(starterCardArray.getFirst(), FaceEnum.FRONT);
        p2Controller.setStarterCard(starterCardArray.get(1), FaceEnum.BACK);
        p1Controller.chooseObjectiveCard((ObjectiveCard) gameController.getGame().getTable().getObjectiveDeck().drawCard());
        p2Controller.chooseObjectiveCard((ObjectiveCard) gameController.getGame().getTable().getObjectiveDeck().drawCard());

        gameController.getGame().getInfo().setFirstPlayer(p1);

        assertEquals(p1, gameController.getGame().getInfo().getFirstPlayer());
        assertNotEquals(p2, gameController.getGame().getInfo().getFirstPlayer());

        gameController.nextTurn();

        assertEquals(p1, gameController.getGame().getInfo().getActivePlayer());
        assertEquals(GameStatusEnum.WAITING_FOR_PLAYERS, gameController.getGame().getInfo().getGameStatus());

        gameController.nextTurn();

        assertEquals(p2, gameController.getGame().getInfo().getActivePlayer());
        assertNotEquals(p1, gameController.getGame().getInfo().getActivePlayer());
    }

    @Test
    @DisplayName("Test valid player reconnection")
    public void validPlayerReconnection() {

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");
        Player p1 = gameController.getPlayerController("p1").getPlayer();
        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        gameController.getGame().getInfo().setActivePlayer(p1);

        gameController.onDisconnect("p1");

        assertTrue(gameController.hasDisconnected("p1"));
        assertFalse(gameController.getGame().getPlayers().contains(p1));
        assertFalse(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertTrue(gameController.getGame().getOfflinePlayers().contains(p1));

        gameController.reconnectPlayer("p1");

        assertTrue(gameController.getGame().getPlayers().contains(p1));
        assertTrue(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertFalse(gameController.getGame().getOfflinePlayers().contains(p1));

    }

    @Test
    @DisplayName("Test valid common objective cards")
    public void validCommonObjectives() {

        gameController.assignCommonObjectives();

        assertFalse(gameController.getGame().getTable().getObjectiveCards().contains(null));
        assertEquals(2, gameController.getGame().getTable().getObjectiveCards().size());
        assertNotEquals(gameController.getGame().getTable().getObjectiveCards().get(0), gameController.getGame().getTable().getObjectiveCards().get(1));

    }
}
