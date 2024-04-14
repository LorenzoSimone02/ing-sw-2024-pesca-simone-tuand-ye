package model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerTest {

    Game game;
    List<Player> activePlayers = new ArrayList<>();
    File starterJson = new File("/resources/assets/startercards");
    File resourceJson = new File("/resources/assets/resourcecards");

    @BeforeEach
    void setup() {
        game = new Game(1);
        activePlayers.add(new Player("p1", game));
        activePlayers.add(new Player("p2", game));
        activePlayers.add(new Player("p3", game));
        activePlayers.add(new Player("p4", game));
    }

    @Test
    @DisplayName("Test valid player nickname")
    public void validNickname() {
        Player player = new Player("test", null);
        assertEquals("test", player.getNickname());
    }

    @Test
    @DisplayName("Test valid player token")
    public void validPlayerToken() {

        Player player1 = new Player("test1", game);
        Player player2 = new Player("test2", game);


        player1.setToken(PlayerToken.RED);
        player2.setToken(PlayerToken.GREEN);

        assertEquals(PlayerToken.RED, player1.getToken());
        assertEquals(PlayerToken.GREEN, player2.getToken());

        assertEquals(PlayerToken.RED.color(), "RED");
        assertEquals(PlayerToken.GREEN.color(), "GREEN");
    }

    @Test
    @DisplayName("Test valid First Player")
    public void validFirstPlayer() {
        Player player = new Player("test", game);
        assertFalse(player.isFirst());
        player.setFirst(true);
        assertTrue(player.isFirst());
    }

    @Test
    @DisplayName("Test valid starter card")
    public void validStarterCard() {

        Player player = new Player("test", game);
        StarterCard starterCard = new StarterCard(starterJson);
        player.setStarterCard(starterCard);

        assertEquals(starterCard, player.getStarterCard());
        assertEquals(40, player.getStarterCard().getXCoord());
        assertEquals(40, player.getStarterCard().getYCoord());
        assertNotNull(player.getStarterCard().getFrontResources());
    }

    @Test
    @DisplayName("Test valid objective card")
    public void validObjectiveCard() {

        Player player = new Player("test", game);
        assertNull(player.getObjectiveCard());
        player.chooseObjectiveCard();
        assertNotNull(player.getObjectiveCard());
    }

    @Test
    @DisplayName("Test valid player card management")
    public void validCardManagement() {

        Player player = new Player("test", game);
        StarterCard starterCard = new StarterCard(starterJson);
        ResourceCard testCard1 = new ResourceCard(resourceJson);
        ResourceCard testCard2 = new ResourceCard(resourceJson);
        ResourceCard testCard3 = new ResourceCard(resourceJson);

        player.setStarterCard(starterCard);
        player.addCardInHand(testCard1);
        player.addCardInHand(testCard2);
        player.addCardInHand(testCard3);

        assertEquals(3, player.getCardsInHand().size());
        assertTrue(player.getCardsInHand().contains(testCard1));
        assertFalse(player.canPlaceCard(starterCard.getXCoord(), starterCard.getYCoord()));
        assertTrue(player.canPlaceCard(41, 41));

        player.placeCard(testCard1, 41, 41);

        assertFalse(player.getCardsInHand().contains(testCard1));
        assertTrue(player.getCardsInHand().contains(testCard2));
        assertTrue(player.getCardsInHand().contains(testCard3));
        assertEquals(2, player.getCardsInHand().size());
        assertFalse(player.canPlaceCard(41, 41));

        player.removeCardInHand(testCard2);

        assertFalse(player.getCardsInHand().contains(testCard2));
        assertEquals(1, player.getCardsInHand().size());

    }

    // TODO: Test whether placing a ResouceCard gives you its resources

}
