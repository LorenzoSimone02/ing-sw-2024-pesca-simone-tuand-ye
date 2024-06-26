package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.controller.exceptions.FullLobbyException;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    ServerNetworkHandler serverNetworkHandler;
    GameController gameController;

    @BeforeEach
    void setup() {
        serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1099, 5000);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");
        gameController.addPlayer("p3");
        gameController.addPlayer("p4");

        gameController.startGame();
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
    @DisplayName("Test valid network handler")
    public void validNetworkHandler() {
        assertNotNull(gameController.getNetworkHandler());
        assertEquals(serverNetworkHandler, gameController.getNetworkHandler());
    }

    @Test
    @DisplayName("Test valid player controllers")
    public void validPlayerControllers() {

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
        assertEquals(4, gameController.getGame().getPlayers().size());
        assertEquals(0, gameController.getGame().getOfflinePlayers().size());
    }

    @Test
    @DisplayName("Test valid players management (add and remove)")
    public void validPlayerManagement() {

        try {
            gameController.addPlayer("p1");
            gameController.addPlayer("p2");
            gameController.addPlayer("p3");
            gameController.addPlayer("p4");
        } catch (FullLobbyException | DuplicatePlayerException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, gameController.getGame().getInfo().getPlayersNumber());

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

        gameController.removePlayer(gameController.getPlayerByNick("p1").orElse(null));
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
