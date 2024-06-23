package it.polimi.ingsw.server.model.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    private Deck resCardDeck;

    @BeforeEach
    void setUp() throws IOException {
        resCardDeck = new Deck();

        for (int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ResourceCard card = new ResourceCard(jsonData);
            resCardDeck.addCard(card);
        }
    }

    @Test
    void shuffleTest() {
        Deck previousResCardDeck = new Deck();
        for (Card card : resCardDeck.getCards()) {
            previousResCardDeck.addCard(card);
        }

        resCardDeck.shuffleDeck();

        if (previousResCardDeck.getCards().equals(resCardDeck.getCards())) {
            fail();

        } else {
            assertTrue(true);
        }
    }

    @Test
    public void drawTest(){

        int prevSize = resCardDeck.getCards().size();
        Card drawnCard = resCardDeck.drawCard();

        if ((resCardDeck.getCards().size() == prevSize - 1) && drawnCard instanceof ResourceCard) {
            assertTrue(true);
        } else {
            fail();
        }
    }

}
