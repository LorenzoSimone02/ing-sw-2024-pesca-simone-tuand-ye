
package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.PlayerController;
import it.polimi.ingsw.server.controller.exceptions.AlreadyTakenColorException;
import it.polimi.ingsw.server.controller.exceptions.WrongTokenException;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private GameController controller;
    private final File starterJson = Paths.get("src/main/resources/assets/startercards/starterCard1.json").toFile();
    private final File resourceJson = Paths.get("src/main/resources/assets/resourcecards/resourceCard2.json").toFile();

    @BeforeEach
    void setup() {
        controller = new GameController(null);
        controller.createGame(1);

        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.addPlayer("p3");
        controller.addPlayer("p4");

        controller.startGame();
    }

    @Test
    @DisplayName("Test valid player nickname")
    public void validNickname() {
        Player player = new Player("test", null);
        assertNotNull(player.getUsername());
        assertEquals("test", player.getUsername());
    }

    @Test
    @DisplayName("Test valid player token")
    public void validPlayerToken() {

        try {
            controller.getPlayerController(controller.getGame().getPlayers().getFirst()).chooseToken(TokenColorEnum.RED);
            controller.getPlayerController(controller.getGame().getPlayers().get(1)).chooseToken(TokenColorEnum.GREEN);
            controller.getPlayerController(controller.getGame().getPlayers().get(2)).chooseToken(TokenColorEnum.BLUE);
            controller.getPlayerController(controller.getGame().getPlayers().get(3)).chooseToken(TokenColorEnum.YELLOW);
        } catch (AlreadyTakenColorException e) {
            fail("Token color already taken.");
        }

        try {
            assertEquals(TokenColorEnum.RED, controller.getGame().getPlayers().getFirst().getToken().getColor());
            assertEquals(TokenColorEnum.GREEN, controller.getGame().getPlayers().get(1).getToken().getColor());
            assertEquals(TokenColorEnum.BLUE, controller.getGame().getPlayers().get(2).getToken().getColor());
            assertEquals(TokenColorEnum.YELLOW, controller.getGame().getPlayers().get(3).getToken().getColor());
        } catch (WrongTokenException e) {
            fail("Wrong token assigned.");
        }
    }

    @Test
    @DisplayName("Test valid starter card")
    public void validStarterCard() {

        controller.removePlayer(controller.getGame().getPlayers().getFirst().getUsername());
        controller.addPlayer("test");
        Player player = controller.getPlayerByNick("test").orElse(null);
        PlayerController playerController = this.controller.getPlayerController(player);

        StarterCard starterCard = new StarterCard(starterJson);
        playerController.setStarterCard(starterCard, FaceEnum.FRONT);

        assertEquals(starterCard, player.getStarterCard());
        assertEquals(40, player.getStarterCard().getXCoord());
        assertEquals(40, player.getStarterCard().getYCoord());
        assertNotNull(player.getStarterCard().getBackResources());
    }

    @Test
    @DisplayName("Test valid objective card")
    public void validObjectiveCard() {

        Player player = new Player("test", controller.getGame());
        assertNull(player.getObjectiveCard());
        //TODO: create an objective card here
        //player.chooseObjectiveCard();
        assertNotNull(player.getObjectiveCard());
    }

    @Test
    @DisplayName("Test valid player card management")
    public void validCardManagement() {
        controller.removePlayer(controller.getGame().getPlayers().getFirst().getUsername());
        controller.addPlayer("test");
        Player player = controller.getPlayerByNick("test").orElse(null);
        PlayerController playerController = this.controller.getPlayerController(player);

        StarterCard starterCard = new StarterCard(starterJson);
        ResourceCard testCard1 = new ResourceCard(resourceJson);
        ResourceCard testCard2 = new ResourceCard(resourceJson);
        ResourceCard testCard3 = new ResourceCard(resourceJson);

        playerController.setStarterCard(starterCard, FaceEnum.BACK);
        player.addCardInHand(testCard1);
        player.addCardInHand(testCard2);
        player.addCardInHand(testCard3);

        assertEquals(3, player.getCardsInHand().size());
        assertTrue(player.getCardsInHand().contains(testCard1));
        assertFalse(controller.getPlayerController(player).canPlaceCard(starterCard.getXCoord(), starterCard.getYCoord()));
        assertTrue(controller.getPlayerController(player).canPlaceCard(41, 41));

        controller.getPlayerController(player).placeCard(testCard1, 41, 41);

        assertFalse(player.getCardsInHand().contains(testCard1));
        assertTrue(player.getCardsInHand().contains(testCard2));
        assertTrue(player.getCardsInHand().contains(testCard3));
        assertEquals(2, player.getCardsInHand().size());
        assertFalse(controller.getPlayerController(player).canPlaceCard(41, 41));

        player.removeCardInHand(testCard2);

        assertFalse(player.getCardsInHand().contains(testCard2));
        assertEquals(1, player.getCardsInHand().size());

    }

    // TODO: Test whether placing a ResouceCard gives you its resources
    /*@Test
    @DisplayName("Test whether placing a ResourceCard gives you its resources")
    public void validResource
*/
}
