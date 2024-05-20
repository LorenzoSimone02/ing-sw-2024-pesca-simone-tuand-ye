package it.polimi.ingsw.model.card;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ResourceCardTest {

    final ArrayList<ResourceCard> resCardArray = new ArrayList<>(40);
    final ArrayList<GoldCard> goldCardArray = new ArrayList<>(40);
    final ArrayList<StarterCard> startCardArray = new ArrayList<>(6);
    final ArrayList<ObjectiveCard> objCardArray = new ArrayList<>(12);

    @BeforeEach
    void setUp() {

        File currFile;

        for (int i = 1; i <= 40; i++){
            currFile = Paths.get("src/main/resources/assets/resourcecards/resourceCard" + i + ".json").toFile();
            ResourceCard card = new ResourceCard(currFile);
            resCardArray.add(card);
        }
        for (int i = 1; i <= 40; i++){
            currFile = Paths.get("src/main/resources/assets/goldcards/goldCard" + i + ".json").toFile();
            GoldCard card = new GoldCard(currFile);
            goldCardArray.add(card);
        }
        for (int i = 1; i <= 6; i++){
            currFile = Paths.get("src/main/resources/assets/startercards/starterCard" + i + ".json").toFile();
            StarterCard card = new StarterCard(currFile);
            startCardArray.add(card);
        }
        for (int i = 1; i <= 12; i++){
            currFile = Paths.get("src/main/resources/assets/objectivecards/objectiveCard" + i + ".json").toFile();
            ObjectiveCard card = new ObjectiveCard(currFile);
            objCardArray.add(card);
        }
    }

    @Test
    @DisplayName("Print Cards Test")
    public void printCardsTest() {
        ResourceCard[][] cards = new ResourceCard[3][3];
        StarterCard starterCard = startCardArray.getFirst();
        cards[0][0] = starterCard;
        cards[0][2] = starterCard;
        cards[1][1] = starterCard;
        Printer.printCardsPlaced(cards);
    }

    @Test
    @DisplayName("Print Resource Card Test")
    public void printResourceCardTest() {
        for (ResourceCard card : resCardArray) {
            System.out.println(card.getId());
            //card.setFace(FaceEnum.BACK);
            Printer.printCard(card);
        }
    }

    @Test
    @DisplayName("Print Gold Card Test")
    public void printGoldCardTest() {
        for (GoldCard card : goldCardArray) {
            System.out.println(card.getId());
            //card.setFace(FaceEnum.BACK);
            Printer.printCard(card);
        }
    }

    @Test
    @DisplayName("Print Starter Card Test")
    public void printStarterCardTest() {
        for (StarterCard card : startCardArray) {
            System.out.println(card.getId());
            card.setFace(FaceEnum.BACK);
            Printer.printCard(card);
        }
    }

    @Test
    @DisplayName("Print Resource Card Test")
    public void printObjectiveCardTest() {
        for (ObjectiveCard card : objCardArray) {
            System.out.println(card.getId());
            card.setFace(FaceEnum.BACK);
            Printer.printCard(card);
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
