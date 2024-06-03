package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameInfoTest {

    Game game;
    GameInfo gameInfo;

    @BeforeEach
    void setUp() {
        game = new Game(1);
        gameInfo = new GameInfo(game, 1);
    }

    @Test
    void getId() {
        assertEquals(1, gameInfo.getId());
    }

    @Test
    void getPlayersNumber() {
        game.getPlayers().add(new Player("test1", game));
        game.getPlayers().add(new Player("test2", game));

        assertEquals(2, gameInfo.getPlayersNumber());
    }

    @Test
    void getAndSetMaxPlayers() {
        assertEquals(4, gameInfo.getMaxPlayers());
        gameInfo.setMaxPlayers(3);
        assertEquals(3, gameInfo.getMaxPlayers());
    }

    @Test
    void getAndSetFirstPlayer() {
        assertNull(gameInfo.getFirstPlayer());
        Player test = new Player("test", game);
        game.getInfo().setFirstPlayer(test);
        assertEquals(test, game.getInfo().getFirstPlayer());
    }

    @Test
    void getAndSetActivePlayer() {
        assertNull(gameInfo.getActivePlayer());
        Player test = new Player("test", game);
        game.getInfo().setActivePlayer(test);
        assertEquals(test, game.getInfo().getActivePlayer());
    }

    @Test
    void getWinners() {
        assertEquals(0, gameInfo.getWinners().size());
    }

    @Test
    void addWinner() {
        Player test = new Player("test", game);
        game.getInfo().addWinner(test);
        assertEquals(1, game.getInfo().getWinners().size());
        assertTrue(game.getInfo().getWinners().contains(test));
    }

    @Test
    void getAndSetGameStatus() {
        assertEquals(GameStatusEnum.WAITING_FOR_PLAYERS, gameInfo.getGameStatus());
        gameInfo.setGameStatus(GameStatusEnum.STARTING);
        assertEquals(GameStatusEnum.STARTING, gameInfo.getGameStatus());
    }

}