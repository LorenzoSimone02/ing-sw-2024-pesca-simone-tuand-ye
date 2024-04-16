package it.polimi.ingsw.model.card;

import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.corner.Corner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class ResourceCardTest {

    final int numOfResourceCards = 10;
    ArrayList<ResourceCard> resCardArray = new ArrayList<>(numOfResourceCards);

    @BeforeEach
    void setUp() {

        ResourceCard currCard;
        File currFile;

        for (int i = 2; i <= numOfResourceCards; i++) {

            //Card selection
            currFile = Paths.get("src/main/resources/assets/resourcecards/testCard" + i + ".json").toFile();
            currCard = new ResourceCard(currFile);

            resCardArray.add(currCard);

        }
    }

    @Test
    @DisplayName("Test turnFace method")
    public void testTurnFaceMethod () {

        FaceEnum prevFace = resCardArray.get(0).getFace();
        resCardArray.get(0).turnCard();

        assertNotEquals(prevFace, resCardArray.get(0).getFace());

    }

    @Test
    @DisplayName("Validate that all corners and their attributes in resourceCards are not null")
    public void validateNotNullAllResourceCards() throws FileNotFoundException {

        for (ResourceCard currCard: resCardArray) {

            if(currCard == null) {
                fail("resCard is null: card "+resCardArray.indexOf(currCard));
            }

            //null attributes tests
            if (currCard.getFace() == null) {
                fail("resCard face is null: card "+resCardArray.indexOf(currCard));

            }
            if (currCard.getBackResource() == null) {
                fail("back resource is null: card "+resCardArray.indexOf(currCard));

            }
            if (currCard.getColor() == null) {
                fail("card color is null: card "+resCardArray.indexOf(currCard));

            }

        }

    }

}
