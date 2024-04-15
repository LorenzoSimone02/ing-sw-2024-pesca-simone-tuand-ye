package model;

import it.polimi.ingsw.server.model.card.GoldCard;

import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

//VEDI CLASSE GameTest.java: CIASCUNO STATUS VA TESTATO INDIPENDENTEMENTE?
public class ImmediateScoreCalculationTest {
    Game game;
    Player activePlayer = new Player("Lorenzo", game);
    File goldJson = new File("/resources/assets/goldcards");
    File starterJson = new File("/resources/assets/startercards");

    @Test
    @DisplayName("Test placement of a resource card")
    public void placeGoldCard() {

        GoldCard goldCard1 = new GoldCard(goldJson);
        StarterCard starterCard = new StarterCard(starterJson);
        int newScore = activePlayer.getScore();

        assertEquals("FRONT", goldCard1.getFace());

        try {
            assertTrue(activePlayer.getResources().containsAll(goldCard1.getResourcesNeeded()));
            activePlayer.placeCard(goldCard1,starterCard.getXCoord()+1,starterCard.getYCoord()+1);
            newScore += goldCard1.getPoints();
        } catch (IllegalCardPlacementException e) {
            fail("Gold card not placed");
        }

        assertEquals(newScore, activePlayer.getScore() + goldCard1.getPoints());

        //TODO: guadagnare punti della carta se si ha un tipo di oggetto sul tavolo
    }
}
