package it.polimi.ingsw.model.card;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ResourceCardTest {

    final int numOfResourceCards = 10;
    final ArrayList<ResourceCard> resCardArray = new ArrayList<>(numOfResourceCards);

    @BeforeEach
    void setUp() {

        ResourceCard currCard;
        File currFile;

        for (int i = 2; i <= numOfResourceCards; i++) {

            //Card selection
            currFile = Paths.get("src/main/resources/assets/resourcecards/resourceCard" + i + ".json").toFile();
            currCard = new ResourceCard(currFile);

            resCardArray.add(currCard);

        }
    }

    @Test
    @DisplayName("Test turnFace method")
    public void testTurnFaceMethod() {

        GameController gameController = new GameController(null);
        gameController.createGame(1);
        gameController.addPlayer("test");
        gameController.startGame();

        if (gameController.getPlayerByNick("test").isPresent()) {
            Card card = gameController.getPlayerByNick("test").get().getCardsInHand().getFirst();
            FaceEnum prevFace = resCardArray.getFirst().getFace();
            gameController.getPlayerController("test").turnCard(card);

            assertNotEquals(prevFace, card.getFace());
        }
    }

    @Test
    @DisplayName("Validate that all corners and their attributes in resourceCards are not null")
    public void validateNotNullAllResourceCards() {

        for (ResourceCard currCard : resCardArray) {

            if (currCard == null) {
                fail("resCard is null");
            }
            //null attributes tests
            if (currCard.getFace() == null) {
                fail("resCard face is null: card " + resCardArray.indexOf(currCard));
            }
            if (currCard.getBackResources() == null) {
                fail("back resource is null: card " + resCardArray.indexOf(currCard));
            }
            if (currCard.getColor() == null) {
                fail("card color is null: card " + resCardArray.indexOf(currCard));
            }
        }
    }
}
