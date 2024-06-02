package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1);
        game.getPlayers().add(new Player("player1", game));
        game.getOfflinePlayers().add(new Player("player2", game));
    }

    @Test
    void getTable() {
        assertNotNull(game.getTable());
    }

    @Test
    void getPlayers() {
        assertNotNull(game.getPlayers());
        assertEquals(game.getPlayers().size(), 1);
        assertEquals("player1", game.getPlayers().getFirst().getUsername());
    }

    @Test
    void getOfflinePlayers() {
        assertNotNull(game.getOfflinePlayers());
        assertEquals(game.getOfflinePlayers().size(), 1);
        assertEquals("player2", game.getOfflinePlayers().getFirst().getUsername());
    }

    @Test
    void getInfo() {
        assertNotNull(game.getInfo());
        GameInfo info = game.getInfo();
        assertEquals(info.getId(), 1);
    }
}