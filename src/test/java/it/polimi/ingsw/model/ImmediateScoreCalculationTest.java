package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

//VEDI CLASSE GameTest.java: CIASCUNO STATUS VA TESTATO INDIPENDENTEMENTE?
public class ImmediateScoreCalculationTest {

    Game game;
    Player activePlayer;
    File goldJson = Paths.get("src/main/resources/assets/goldcards/goldCard1.json").toFile();
    File starterJson = Paths.get("src/main/resources/assets/startercards/testCard1.json").toFile();

    @BeforeEach
    void setup() {
        Game game = new Game(1);
        activePlayer = new Player("Lorenzo", game);
        game.addPlayer(activePlayer);
    }

    @Test
    @DisplayName("Test placement of a resource card")
    public void placeGoldCard() {

        GoldCard goldCard1 = new GoldCard(goldJson);
        StarterCard starterCard = new StarterCard(starterJson);
        activePlayer.setStarterCard(starterCard);
        activePlayer.addResource(new Resource(ResourceTypeEnum.ANIMAL));
        int oldScore = activePlayer.getScore();

        assertEquals(FaceEnum.FRONT, goldCard1.getFace());

        try {
            assertTrue(activePlayer.getResources().containsAll(goldCard1.getResourcesNeeded()));
            activePlayer.placeCard(goldCard1, starterCard.getXCoord() + 1, starterCard.getYCoord() + 1);
        } catch (IllegalCardPlacementException e) {
            fail("Gold card not placed");
        }

        assertEquals(oldScore + goldCard1.getPoints(), activePlayer.getScore());

        //TODO: guadagnare punti della carta se si ha un tipo di oggetto sul tavolo
    }
}
