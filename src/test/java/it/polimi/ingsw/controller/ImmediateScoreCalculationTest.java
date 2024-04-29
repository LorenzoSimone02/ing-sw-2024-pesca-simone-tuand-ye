package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.controller.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.controller.exceptions.IllegalGoldCardPointsException;
import it.polimi.ingsw.server.model.card.goldstrategies.GoldStrategyType;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ImmediateScoreCalculationTest {

    private GameController controller;
    private Player activePlayer;
    private final File goldObjectJson1 = Paths.get("src/main/resources/assets/goldcards/goldCard1.json").toFile();
    private final File goldCornerJson2 = Paths.get("src/main/resources/assets/goldcards/goldCard4.json").toFile();
    private final File starterJson = Paths.get("src/main/resources/assets/startercards/starterCard1.json").toFile();

    @BeforeEach
    void setup() {
        controller = new GameController(null);
        controller.createGame(1);
        controller.addPlayer("Lorenzo");
        activePlayer = controller.getPlayerByNick("Lorenzo").orElse(null);
    }

    @Test
    @DisplayName("Test placement of a resource card")
    public void placeGoldCard() {

        GoldCard goldCard1 = new GoldCard(goldObjectJson1);
        GoldCard goldCard2 = new GoldCard(goldCornerJson2);
        StarterCard starterCard = new StarterCard(starterJson);
        controller.getPlayerController(activePlayer).setStarterCard(starterCard);

        activePlayer.addObject(new Object(ObjectTypeEnum.QUILL));
        activePlayer.addObject(new Object(ObjectTypeEnum.QUILL));
        activePlayer.addResource(new Resource(ResourceTypeEnum.FUNGI));
        activePlayer.addResource(new Resource(ResourceTypeEnum.FUNGI));
        activePlayer.addResource(new Resource(ResourceTypeEnum.FUNGI));
        activePlayer.addResource(new Resource(ResourceTypeEnum.ANIMAL));

        int oldScore = activePlayer.getScore();

        assertEquals(FaceEnum.FRONT, goldCard1.getFace());
        assertEquals(FaceEnum.FRONT, goldCard2.getFace());

        try {
            assertTrue(activePlayer.getResources().containsAll(goldCard1.getResourcesNeeded()));
            controller.getPlayerController(activePlayer).placeCard(goldCard1, starterCard.getXCoord() + 1, starterCard.getYCoord() + 1);
        } catch (IllegalCardPlacementException e) {
            fail("Gold card not placed");
        }

        //Test object count GoldCard
        try{
            controller.getPlayerController(activePlayer).placeCard(goldCard1, starterCard.getXCoord() + 1, starterCard.getYCoord() + 1);
            assertEquals(goldCard1.getPointsStrategy(), GoldStrategyType.OBJECT_COUNT_QUILL);
            assertEquals(oldScore + (goldCard1.getPointsStrategy().getStrategy().calculatePoints(activePlayer, starterCard.getXCoord() + 1, starterCard.getYCoord() + 1)),
                    controller.getPlayerController(activePlayer).getPlayer().getScore());
        } catch (IllegalGoldCardPointsException e) {
            fail("GoldCard object count points not assigned");
        }

        //Test corner count GoldCard
        try{
            controller.getPlayerController(activePlayer).placeCard(goldCard2, starterCard.getXCoord() - 1, starterCard.getYCoord() - 1);
            assertEquals(goldCard1.getPointsStrategy(), GoldStrategyType.CORNER_COUNT);
            assertEquals(oldScore + (goldCard2.getPointsStrategy().getStrategy().calculatePoints(activePlayer, starterCard.getXCoord() - 1, starterCard.getYCoord() - 1)),
                    controller.getPlayerController(activePlayer).getPlayer().getScore());
        } catch (IllegalGoldCardPointsException e) {
            fail("GoldCard corner count points not assigned");
        }
    }
}