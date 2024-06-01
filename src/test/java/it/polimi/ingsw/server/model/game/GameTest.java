package it.polimi.ingsw.server.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1);
    }

    @Test
    void getTable() {

    }

    @Test
    void getPlayers() {
        assertNotNull(game.getPlayers());
        assertTrue(game.getPlayers().isEmpty());
    }

    @Test
    void getOfflinePlayers() {
    }

    @Test
    void getInfo() {
    }
}