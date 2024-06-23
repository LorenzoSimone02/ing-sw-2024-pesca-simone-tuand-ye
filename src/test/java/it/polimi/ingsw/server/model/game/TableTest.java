package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.Deck;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    Table table;
    Deck resourceDeck;
    Deck goldDeck;
    Deck objectiveDeck;
    Deck starterDeck;



    @BeforeEach
    void setUp() throws IOException {
        table = new Table();
        resourceDeck = new Deck();
        goldDeck = new Deck();
        objectiveDeck = new Deck();
        starterDeck = new Deck();

        for(int i = 1; i <= 16; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveDeck.addCard(card);
        }
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
        table.addCardOnGround(objectiveDeck.drawCard());

        if (!table.getCardsOnGround().isEmpty()) assertTrue(true);

        if (table.getCardsOnGround().isEmpty()) fail();
    }

    @Test
    void removeCardOnGround() {
        Card drawnCard = objectiveDeck.drawCard();

        table.addCardOnGround(drawnCard);
        table.removeCardOnGround(drawnCard);

        if (table.getCardsOnGround().isEmpty()) assertTrue(true);

        if (!table.getCardsOnGround().isEmpty()) fail();




    }

    @Test
    void getObjectiveCards() {
        assertNotNull(table.getObjectiveCards());
    }

    @Test
    void addObjectiveCard() {
        table.addObjectiveCard((ObjectiveCard)objectiveDeck.drawCard());

        assertTrue(true);

    }
}