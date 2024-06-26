package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.exceptions.*;
import it.polimi.ingsw.server.model.card.GoldCard;
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
            controller.nextTurn();
        }

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.CHOOSING_COLOR);

        for (int i = 0; i < 4; i++) {
            assertNotNull(controller.getGame().getPlayers().get(i).getObjectiveCard());
            controller.nextTurn();
        }

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);

        for (int i = 0; i < 4; i++) {
            // TODO: implement personal turn move choices
            controller.nextTurn();

        }

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.PLAYING);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.PLAYING);

        controller.endGame();

        controller.getGame().getInfo().setGameStatus(GameStatusEnum.ENDING);
        assertEquals(controller.getGame().getInfo().getGameStatus(), GameStatusEnum.ENDING);

    }
}
