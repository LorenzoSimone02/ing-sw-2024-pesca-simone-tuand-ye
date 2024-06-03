package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameControllerTest {

    ServerNetworkHandler serverNetworkHandler;
    GameController gameController;

    @BeforeEach
    void setup() {
        serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1099, 5000);
        serverNetworkHandler.start();
        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);
    }

    @AfterEach
    void tearDown() {
        serverNetworkHandler.stop();
    }

    @Test
    public void test(){

    }
}
