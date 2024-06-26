package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CardSaveTest {

    private ServerNetworkHandler serverNetworkHandler;
    private GameController gameController;
    private ArrayList<ResourceCard> resourceCardArray;
    private static int id = 0;

    @BeforeEach
    void setUp() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("Server", 1099 + id, 5001);
        serverNetworkHandler.start();

        gameController = new GameController(serverNetworkHandler);
        gameController.createGame(1);

        resourceCardArray = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
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
        id++;
    }

    @AfterEach
    void tearDown() {
        serverNetworkHandler.stop();
    }

    @Test
    void getId() {

        CardSave cardSave = new CardSave(resourceCardArray.get(1));

        assertNotNull(cardSave);
        assertEquals(resourceCardArray.get(1).getId(), cardSave.getId());

    }

    @Test
    void getFace() {

        gameController.addPlayer("p1");

        CardSave cardSave = new CardSave(resourceCardArray.get(1));
        assertEquals(resourceCardArray.get(1).getFace(), FaceEnum.valueOf(cardSave.getFace()));

        gameController.getPlayerController("p1").turnCard(resourceCardArray.get(1));
        assertNotEquals(resourceCardArray.get(1).getFace(), FaceEnum.valueOf(cardSave.getFace()));

    }
}