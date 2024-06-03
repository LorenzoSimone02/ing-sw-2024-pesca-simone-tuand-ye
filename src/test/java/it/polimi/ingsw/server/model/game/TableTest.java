package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TableTest {

    Table table;
    Deck resourceDeck;
    Deck goldDeck;
    Deck objectiveDeck;
    Deck starterDeck;

    @BeforeEach
    void setUp() {
        table = new Table();
        resourceDeck = new Deck();
        goldDeck = new Deck();
        objectiveDeck = new Deck();
        starterDeck = new Deck();
    }

    @Test
    void getAndSetResourceDeck() {
        table.setResourceDeck(resourceDeck);
        assertNotNull(table.getResourceDeck());
        assertEquals(resourceDeck, table.getResourceDeck());
    }

    @Test
    void getAndSetGoldDeck() {
        table.setGoldDeck(goldDeck);
        assertNotNull(table.getGoldDeck());
        assertEquals(goldDeck, table.getGoldDeck());
    }

    @Test
    void getAndSetObjectiveDeck() {
        table.setObjectiveDeck(objectiveDeck);
        assertNotNull(table.getObjectiveDeck());
        assertEquals(objectiveDeck, table.getObjectiveDeck());
    }

    @Test
    void getAndSetStarterDeck() {
        table.setStarterDeck(starterDeck);
        assertNotNull(table.getStarterDeck());
        assertEquals(starterDeck, table.getStarterDeck());
    }

    @Test
    void getCardsOnGround() {
        assertNotNull(table.getCardsOnGround());
    }

    @Test
    void addCardOnGround() {
    }

    @Test
    void getObjectiveCards() {
    }

    @Test
    void addObjectiveCard() {
    }
}