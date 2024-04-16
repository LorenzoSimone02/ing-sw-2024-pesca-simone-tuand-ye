package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.exceptions.AlreadyTakenColorException;
import it.polimi.ingsw.server.model.exceptions.WrongTokenException;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.objectives.Objective;
import it.polimi.ingsw.server.model.objectives.ObjectiveType;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Game game;
    File starterJson = Paths.get("src/main/resources/assets/startercards/resourceCard1.json").toFile();
    File resourceJson = Paths.get("src/main/resources/assets/resourcecards/resourceCard2.json").toFile();

    @BeforeEach
    void setup() {
        game = new Game(1);

        game.addPlayer(new Player("p1", game));
        game.addPlayer(new Player("p2", game));
        game.addPlayer(new Player("p3", game));
        game.addPlayer(new Player("p4", game));

        game.startGame();
    }

    @Test
    @DisplayName("Test valid player nickname")
    public void validNickname() {
        Player player = new Player("test", null);
        assertNotNull(player.getNickname());
        assertEquals("test", player.getNickname());
    }

    @Test
    @DisplayName("Test valid player token")
    public void validPlayerToken() {

        try {
            game.getPlayers().getFirst().chooseToken(new PlayerToken(TokenColorEnum.RED));
            game.getPlayers().get(1).chooseToken(new PlayerToken(TokenColorEnum.GREEN));
            game.getPlayers().get(2).chooseToken(new PlayerToken(TokenColorEnum.BLUE));
            game.getPlayers().get(3).chooseToken(new PlayerToken(TokenColorEnum.YELLOW));
        } catch (AlreadyTakenColorException e) {
            fail("Token color already taken.");
        }

        try {
            assertEquals(TokenColorEnum.RED, game.getPlayers().getFirst().getToken().getColor());
            assertEquals(TokenColorEnum.GREEN, game.getPlayers().get(1).getToken().getColor());
            assertEquals(TokenColorEnum.BLUE, game.getPlayers().get(2).getToken().getColor());
            assertEquals(TokenColorEnum.YELLOW, game.getPlayers().get(3).getToken().getColor());
        } catch (WrongTokenException e) {
            fail("Wrong token assigned.");
        }
    }

    @Test
    @DisplayName("Test valid First Player")
    public void validFirstPlayer() {

        for(Player p : game.getPlayers()) {
            assertFalse(p.isFirst());
        }

        game.getPlayers().get(2).setFirst(true);
        assertTrue(game.getPlayers().get(2).isFirst());
        for(int i = 0; i < game.getInfo().getPlayersNumber() && i != 2; i++) {
            assertFalse(game.getPlayers().get(i).isFirst());
        }

        assertEquals(game.getInfo().getFirstPlayer(), game.getPlayers().get(2));
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
