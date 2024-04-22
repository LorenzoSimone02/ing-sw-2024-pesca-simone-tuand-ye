package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.controller.exceptions.EmptyDeckException;
import it.polimi.ingsw.server.model.game.Table;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    private Table table;
    final GameController controller = new GameController(null);

    @BeforeEach
    void setup() {
        controller.createGame(1);
        table = controller.getGame().getTable();
    }

    @Test
    @DisplayName("Test valid decks management")
    public void validDeckList() {

        controller.startGame();

        assertEquals((40 - (2 * controller.getGame().getInfo().getPlayersNumber()) - 2), controller.getGame().getTable().getResourceDeck().getCards().size());
        assertEquals((40 - (controller.getGame().getInfo().getPlayersNumber()) - 2), controller.getGame().getTable().getGoldDeck().getCards().size());

    }

    @Test
    @DisplayName("Test valid initial on ground cards")
    public void validInitialOnGroundCards() {

        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.startGame();

        int numOfGoldCards = 0;
        int numOfResourceCards = 0;

        for (Card card : table.getCardsOnGround()) {
            if (card instanceof GoldCard) {
                numOfGoldCards++;
            } else if (card instanceof ResourceCard) {
                numOfResourceCards++;
            }
        }

        assertEquals(2, numOfGoldCards);
        assertEquals(2, numOfResourceCards);
        assertEquals(2, controller.getGame().getObjectiveCards().size());

    }

    @Test
    @DisplayName("Test valid draw card action")
    public void validDrawResourceCard() {

        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.startGame();

        for (Player player : controller.getGame().getPlayers()) {
            try {
                player.addCardInHand(table.getResourceDeck().drawCard());
            } catch (EmptyDeckException e) {
                throw new RuntimeException();
            }
        }
    }

    @Test
    @DisplayName("Test valid draw card action")
    public void validDrawGoldCard() {

        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.startGame();

        for (Player player : controller.getGame().getPlayers()) {
            try {
                player.addCardInHand(table.getGoldDeck().drawCard());
            } catch (EmptyDeckException e) {
                throw new RuntimeException();
            }
        }
    }
}
