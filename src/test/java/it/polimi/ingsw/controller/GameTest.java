package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.exceptions.*;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private GameController controller;
    private final File objectiveJson = Paths.get("src/main/resources/assets/objectivecards/objectiveCard1.json").toFile();

    @BeforeEach
    void setup() {
        controller = new GameController(null);
        controller.createGame(1);
    }

    @Test
    @DisplayName("Test valid players management")
    public void validPlayerList() {

        for (int i = 0; i < 4; i++) {

            try {
                controller.addPlayer("p" + i);
            } catch (IllegalOperationForStateException e) {
                fail("Player not added");
            }
            if (controller.getGame().getInfo().getPlayersNumber() != i + 1) {
                fail("Tried to add a player but the number of players is not correct");
            }
            if (!controller.getGame().getPlayers().get(i).getUsername().equals("p" + i)) {
                fail("Wrong player added");
            }
            assertEquals(controller.getGame().getPlayers().get(i), controller.getPlayerByNick("p" + i).orElse(null));
        }

        assertThrows(DuplicatePlayerException.class, () -> controller.addPlayer("p0"));
        assertThrows(DuplicatePlayerException.class, () -> controller.addPlayer("p1"));
        assertThrows(DuplicatePlayerException.class, () -> controller.addPlayer("p2"));
        assertThrows(DuplicatePlayerException.class, () -> controller.addPlayer("p3"));
        assertThrows(FullLobbyException.class, () -> controller.addPlayer("p4"));

    }

    @Test
    @DisplayName("Test valid players management")
    public void validPlayerManagement() {

        try {
            controller.addPlayer("p1");
            controller.addPlayer("p2");
            controller.addPlayer("p3");
            controller.addPlayer("p4");
        } catch (FullLobbyException | DuplicatePlayerException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, controller.getGame().getInfo().getPlayersNumber());
        controller.removePlayer(controller.getGame().getPlayers().get(1).getUsername());
        for (int i = 0; i < controller.getGame().getInfo().getPlayersNumber(); i++) {
            assertNotEquals("test2", controller.getGame().getPlayers().get(i).getUsername());
        }
        assertEquals(3, controller.getGame().getInfo().getPlayersNumber());

    }

    @Test
    @DisplayName("Test valid common objective cards")
    public void validCommonObjectives() {

        ObjectiveCard objective1 = new ObjectiveCard(objectiveJson);
        ObjectiveCard objective2 = new ObjectiveCard(objectiveJson);

        controller.getGame().addObjectiveCard(objective1);
        controller.getGame().addObjectiveCard(objective2);

        assertTrue(controller.getGame().getObjectiveCards().contains(objective1));
        assertTrue(controller.getGame().getObjectiveCards().contains(objective2));

        try {
            ObjectiveCard extra = new ObjectiveCard(objectiveJson);
            controller.getGame().addObjectiveCard(extra);
        } catch (IllegalObjectiveException e) {
            fail("Illegal objective card added.");
        }

    }

    //QUESTI SONO TEST CHE SI FANNO NELLA FASE STARTING DEL GAME
    @Test
    @DisplayName("Test valid initial player cards in hand")
    public void validInitialPlayerCardsInHand() {

        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.startGame();

        int numOfGoldCards = 0;
        int numOfResourceCards = 0;

        for (Player player : controller.getGame().getPlayers()) {
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
    @DisplayName("Test valid game status")
    public void validGameStatus() {

        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.WAITING_FOR_PLAYERS);

        try {
            controller.addPlayer("p1");
            controller.addPlayer("p2");
            controller.addPlayer("p3");
            controller.addPlayer("p4");
        } catch (FullLobbyException | DuplicatePlayerException e) {
            throw new RuntimeException(e);
        }

        try {
            controller.startGame();
            assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.STARTING);
        } catch (GameStartException e) {
            assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.ERROR);
            fail("Game not correctly started.");
        }

        for (int i = 0; i < 4; i++) {
            assertNotNull(controller.getGame().getPlayers().get(i).getToken());
            try {
                controller.nextTurn();
            } catch (EndGameException e) {
                throw new RuntimeException(e);
            }
        }

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.CHOOSING_COLOR);

        for (int i = 0; i < 4; i++) {
            assertNotNull(controller.getGame().getPlayers().get(i).getObjectiveCard());
            try {
                controller.nextTurn();
            } catch (EndGameException e) {
                throw new RuntimeException(e);
            }
        }

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);

        for (int i = 0; i < 4; i++) {
            // TODO: implement personal turn move choices
            try {
                controller.nextTurn();
            } catch (EndGameException e) {
                throw new RuntimeException(e);
            }
        }

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.PLAYING);

        controller.endGame();

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.ENDING);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.ENDING);

    }
}
