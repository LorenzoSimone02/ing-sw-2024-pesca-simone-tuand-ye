package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.rmi.RMIClientConnection;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.controller.exceptions.FullLobbyException;
import it.polimi.ingsw.server.controller.exceptions.GameStartException;
import it.polimi.ingsw.server.controller.exceptions.IllegalOperationForStateException;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.management.remote.rmi.RMIServer;

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

    @BeforeEach
    void setup() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1099, 5001);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        resourceCardArray = new ArrayList<>();
        goldCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();

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

        ResourceCard chosenCard = resourceCardArray.get(0);
        assertEquals(chosenCard.getId(), resourceCardArray.get(0).getId());
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

            try {
                gameController.addPlayer("p" + i);
            } catch (IllegalOperationForStateException e) {
                fail("Player not added");
            }
            if (gameController.getGame().getInfo().getPlayersNumber() != i + 1) {
                fail("Tried to add a player but the number of players is not correct");
            }
            if (!gameController.getGame().getPlayers().get(i).getUsername().equals("p" + i)) {
                fail("Wrong player added");
            }
            assertEquals(gameController.getGame().getPlayers().get(i), gameController.getPlayerByNick("p" + i).orElse(null));
        }

        assertThrows(DuplicatePlayerException.class, () -> gameController.addPlayer("p0"));
        assertThrows(DuplicatePlayerException.class, () -> gameController.addPlayer("p1"));
        assertThrows(DuplicatePlayerException.class, () -> gameController.addPlayer("p2"));
        assertThrows(DuplicatePlayerException.class, () -> gameController.addPlayer("p3"));
        assertThrows(FullLobbyException.class, () -> gameController.addPlayer("p4"));

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

        try {
            gameController.addPlayer("p1");
            gameController.addPlayer("p2");
            gameController.addPlayer("p3");
            gameController.addPlayer("p4");
        } catch (FullLobbyException | DuplicatePlayerException e) {
            throw new RuntimeException(e);
        }

        try {
            gameController.startGame();
            assertEquals(gameController.getGame().getInfo().getGameStatus(), GameStatusEnum.STARTING);
        } catch (GameStartException e) {
            assertEquals(gameController.getGame().getInfo().getGameStatus(), GameStatusEnum.ERROR);
            fail("Game not correctly started.");
        }

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        assertEquals(GameStatusEnum.CHOOSING_COLOR, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_STARTER_FACE);
        assertEquals(GameStatusEnum.CHOOSING_STARTER_FACE, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);
        assertEquals(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.LAST_TURN);
        assertEquals(GameStatusEnum.LAST_TURN, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        assertEquals(GameStatusEnum.PLAYING, gameController.getGame().getInfo().getGameStatus());

        gameController.endGame();

        assertEquals(GameStatusEnum.ENDING, gameController.getGame().getInfo().getGameStatus());

    }

    @Test
    @DisplayName("Test valid initial player in hand cards")
    public void validInitialPlayerCardsInHand() {

        int numOfGoldCards = 0;
        int numOfResourceCards = 0;

        for (Player player : gameController.getGame().getPlayers()) {
            for (int i = 0; i < player.getCardsInHand().size(); i++) {
                for(int j = 0; j < player.getCardsInHand().size(); j++) {
                    if (i != j) {
                        assertNotEquals(player.getCardsInHand().get(i), player.getCardsInHand().get(j));
                    }
                }
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

        gameController.getGame().getInfo().setFirstPlayer(gameController.getPlayerByNick("p1").orElse(null));

        assertEquals(gameController.getPlayerByNick("p1").orElse(null), gameController.getGame().getInfo().getFirstPlayer());
        assertNotEquals(gameController.getPlayerByNick("p2").orElse(null), gameController.getGame().getInfo().getFirstPlayer());
        assertEquals(gameController.getPlayerByNick("p1").orElse(null), gameController.getGame().getInfo().getActivePlayer());
        assertEquals(GameStatusEnum.PLAYING, gameController.getGame().getInfo().getGameStatus());

        gameController.nextTurn();

        assertEquals(gameController.getPlayerByNick("p2").orElse(null), gameController.getGame().getInfo().getActivePlayer());
        assertNotEquals(gameController.getPlayerByNick("p1").orElse(null), gameController.getGame().getInfo().getActivePlayer());
        assertEquals(GameStatusEnum.PLAYING, gameController.getGame().getInfo().getGameStatus());

    }

    @Test
    @DisplayName("Test valid player reconnection")
    public void validPlayerReconnection() {

        gameController.addPlayer("p1");
        Player p1 = gameController.getPlayerController("p1").getPlayer();
        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        gameController.getGame().getInfo().setActivePlayer(p1);

        gameController.onDisconnect("p1");

        assertTrue(gameController.hasDisconnected("p1"));
        assertFalse(gameController.getGame().getPlayers().contains(gameController.getPlayerByNick("p1").orElse(null)));
        assertFalse(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertTrue(gameController.getGame().getOfflinePlayers().contains(gameController.getPlayerByNick("p1").orElse(null)));

        gameController.reconnectPlayer("p1");

        assertTrue(gameController.getGame().getPlayers().contains(gameController.getPlayerByNick("p1").orElse(null)));
        assertTrue(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertFalse(gameController.getGame().getOfflinePlayers().contains(gameController.getPlayerByNick("p1").orElse(null)));

    }

    @Test
    @DisplayName("Test valid common objective cards")
    public void validCommonObjectives() {

        gameController.assignCommonObjectives();

        assertFalse(gameController.getGame().getTable().getObjectiveCards().contains(null));
        assertEquals(2, gameController.getGame().getTable().getObjectiveCards().size());
        assertNotEquals(gameController.getGame().getTable().getObjectiveCards().get(0), gameController.getGame().getTable().getObjectiveCards().get(1));

    }

    //TODO: mo' lo finisco + alcuni altri test
    @Test
    @DisplayName("Test valid game ending")
    public void validGameEnding() {

        gameController.getPlayerController("p1").getPlayer().setScore(69);
        
    }

}
