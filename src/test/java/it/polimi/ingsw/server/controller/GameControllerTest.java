package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.controller.exceptions.FullLobbyException;
import it.polimi.ingsw.server.controller.exceptions.IllegalOperationForStateException;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.GameInfo;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
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

    @BeforeEach
    void setup() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1099, 5001);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        gameController.addPlayer("p1");
        gameController.addPlayer("p2");

        gameController.startGame();

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

        Card chosenCard = gameController.getCardById(resourceCardArray.get(0).getId());
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

        assertEquals(2, gameController.getPlayerControllers().size());
        assertNotNull(gameController.getPlayerController(gameController.getPlayerByNick("p1").orElse(null)));
        assertNotNull(gameController.getPlayerController(gameController.getPlayerByNick("p2").orElse(null)));

        gameController.removePlayer(gameController.getPlayerByNick("p1").orElse(null));
        assertFalse(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertEquals(1, gameController.getPlayerControllers().size());

    }

    @Test
    @DisplayName("Test valid player number")
    public void validPlayerNumber() {

        assertEquals(2, gameController.getGame().getPlayers().size());
        assertEquals(0, gameController.getGame().getOfflinePlayers().size());
    }

    @Test
    @DisplayName("Test valid game status")
    public void validGameStatus() {

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        assertEquals(GameStatusEnum.CHOOSING_COLOR, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.STARTING);
        assertEquals(GameStatusEnum.STARTING, gameController.getGame().getInfo().getGameStatus());

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.WAITING_FOR_PLAYERS);
        assertEquals(GameStatusEnum.WAITING_FOR_PLAYERS, gameController.getGame().getInfo().getGameStatus());

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

        gameController.getPlayerByNick("p1").orElse(null).addCardInHand(goldCardArray.get(0));
        gameController.getPlayerByNick("p1").orElse(null).addCardInHand(resourceCardArray.get(0));
        gameController.getPlayerByNick("p1").orElse(null).addCardInHand(resourceCardArray.get(1));

        for (int i = 0; i < gameController.getPlayerByNick("p1").orElse(null).getCardsInHand().size(); i++) {
//            for(int j = 0; j < gameController.getPlayerByNick("p1").orElse(null).getCardsInHand().size(); j++) {
//                if (i != j) {
//                    assertNotEquals(gameController.getPlayerByNick("p1").orElse(null).getCardsInHand().get(i), gameController.getPlayerByNick("p1").orElse(null).getCardsInHand().get(j));
//                }
//            }
            if (gameController.getPlayerByNick("p1").orElse(null).getCardsInHand().get(i) instanceof GoldCard) {
                numOfGoldCards++;
            } else if (gameController.getPlayerByNick("p1").orElse(null).getCardsInHand().get(i) instanceof ResourceCard) {
                numOfResourceCards++;
            }
        }

        assertEquals(1, numOfGoldCards);
        assertEquals(2, numOfResourceCards);

    }

    @Test
    @DisplayName("Test valid first player + turn management")
    public void validFirstPlayer() {

        gameController.startGame();

        gameController.getGame().getInfo().setFirstPlayer(gameController.getPlayerByNick("p1").orElse(null));
        gameController.getGame().getInfo().setActivePlayer(gameController.getPlayerByNick("p1").orElse(null));

        assertEquals(gameController.getPlayerByNick("p1").orElse(null), gameController.getGame().getInfo().getFirstPlayer());
        assertEquals(gameController.getPlayerByNick("p1").orElse(null), gameController.getGame().getInfo().getActivePlayer());
        assertNotEquals(gameController.getPlayerByNick("p2").orElse(null), gameController.getGame().getInfo().getFirstPlayer());
        assertNotEquals(gameController.getPlayerByNick("p2").orElse(null), gameController.getGame().getInfo().getActivePlayer());

        gameController.nextTurn();

        assertEquals(gameController.getPlayerByNick("p2").orElse(null), gameController.getGame().getInfo().getActivePlayer());
        assertNotEquals(gameController.getPlayerByNick("p1").orElse(null), gameController.getGame().getInfo().getActivePlayer());

    }

    @Test
    @DisplayName("Test valid player reconnection")
    public void validPlayerReconnection() {

        gameController.startGame();

        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        gameController.getGame().getInfo().setActivePlayer(gameController.getPlayerByNick("p1").orElse(null));

        gameController.onDisconnect("p1");

        assertTrue(gameController.hasDisconnected("p1"));
        assertFalse(gameController.getGame().getPlayers().contains(gameController.getPlayerByNick("p1").orElse(null)));
        assertFalse(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));

        gameController.reconnectPlayer("p1");

        assertTrue(gameController.getPlayerControllers().contains(gameController.getPlayerController("p1")));
        assertFalse(gameController.getGame().getOfflinePlayers().contains(gameController.getPlayerByNick("p1").orElse(null)));

    }

    @Test
    @DisplayName("Test valid common objective cards")
    public void validCommonObjectives() {

        assertFalse(gameController.getGame().getTable().getObjectiveCards().contains(null));
        assertEquals(2, gameController.getGame().getTable().getObjectiveCards().size());
        assertNotEquals(gameController.getGame().getTable().getObjectiveCards().get(0), gameController.getGame().getTable().getObjectiveCards().get(1));

    }

    @Test
    @DisplayName("Test valid game ending")
    public void validGameEnding() {

        gameController.startGame();

        gameController.getGame().getInfo().setFirstPlayer(gameController.getPlayerByNick("p1").orElse(null));
        gameController.getGame().getInfo().setActivePlayer(gameController.getPlayerByNick("p2").orElse(null));
        gameController.getPlayerController("p1").getPlayer().setScore(69);
        gameController.getPlayerController("p2").getPlayer().setScore(42);
        gameController.getGame().getInfo().setGameStatus(GameStatusEnum.LAST_TURN);
        gameController.checkEndCondition();
        gameController.endGame();

        assertTrue(gameController.getGame().getInfo().getWinners().contains(gameController.getPlayerController("p1").getPlayer()));
        assertFalse(gameController.getGame().getInfo().getWinners().contains(gameController.getPlayerController("p2").getPlayer()));

    }

    @Test
    @DisplayName("Test valid existing game restoring")
    public void validGameSaveRestoring() {

        gameController.getGame().getInfo().setMaxPlayers(2);
        gameController.startGame();

        GameInfo oldInfo = gameController.getGame().getInfo();

        gameController.saveGameToFile();

        serverNetworkHandler.stop();

        serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1099, 5001);
        serverNetworkHandler.start();
        gameController = new GameController(serverNetworkHandler);

        gameController.createGame(1);
        gameController.getGame().getInfo().setMaxPlayers(2);
        gameController.addPlayer("p1");
        gameController.addPlayer("p2");

        gameController.checkStartCondition();

        assertEquals(oldInfo, gameController.getGame().getInfo());

    }

}
