package model;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameTest {
    Game game;
    List<Player> activePlayers = new ArrayList<>();
    File objectiveJson = new File("/resources/assets/objectivecards");

    @BeforeEach
    void setup() {
        game = new Game(1);
    }

    @Test
    @DisplayName("Test valid players management")
    public void validPlayerManagement() {

        game.addPlayer(new Player("p1", game));
        game.addPlayer(new Player("p2", game));
        game.addPlayer(new Player("p3", game));
        game.addPlayer(new Player("p4", game));

        assertEquals(3, game.getPlayers().size());
        game.removePlayer(game.getPlayers().get(1));
        for (Player player : activePlayers) {
            assertNotEquals("test2", player.getNickname());
        }
        assertEquals(2, game.getPlayers().size());

        assertTrue(game.getPlayers().contains(game.getInfo().getFirstPlayer()));
    }

    @Test
    @DisplayName("Test valid global objective cards")
    public void validObjectives(){

        ObjectiveCard objective1 = new ObjectiveCard(objectiveJson);
        ObjectiveCard objective2 = new ObjectiveCard(objectiveJson);

        game.addObjectiveCard(objective1);
        game.addObjectiveCard(objective2);

        assertTrue(game.getObjectiveCards().contains(objective1));
        assertTrue(game.getObjectiveCards().contains(objective2));

    }

    @Test
    @DisplayName("Test valid game status")
    public void validGameStatus(){

        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.WAITING_FOR_PLAYERS);

        game.addPlayer(new Player("p1", game));
        game.addPlayer(new Player("p2", game));
        game.addPlayer(new Player("p3", game));
        game.addPlayer(new Player("p4", game));

        try {
            game.startGame();
            assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.STARTING);
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.ERROR);
        }

        for (Player player : game.getPlayers()) {
            assertNotNull(player.getToken());
        }

        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.CHOOSING_COLOR);

        for (Player player : game.getPlayers()) {
            assertNotNull(player.getObjectiveCard());
        }

        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);

        

        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.PLAYING);

        game.endGame();

        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.ENDING);

    }
}
