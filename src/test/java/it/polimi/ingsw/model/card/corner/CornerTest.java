package it.polimi.ingsw.model.corner;

import com.google.gson.Gson;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.model.game.Game;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CornerTest {

    final int numOfResourceCards = 10;

    @Test
    @DisplayName("Test Corner.setVisible() method")
    public void testSetVisibleMethod() {

        File currFile = Paths.get("src/main/resources/assets/resourcecards/testCard2.json").toFile();
        ResourceCard currCard = new ResourceCard(currFile);
        List<Corner> currCorners = currCard.getCorners();

        boolean prevVisibility = currCorners.get(2).isVisible();
        currCorners.get(2).setVisible(!prevVisibility);

        assertEquals(!prevVisibility, currCorners.get(2).isVisible());

    }

    @Test
    @DisplayName("Validate that corners in a single resource card have 4x2 different CornerLocationEnum")
    public void validateAllCornersLocation() {

        File currFile = Paths.get("src/main/resources/assets/resourcecards/testCard2.json").toFile();
        ResourceCard currCard = new ResourceCard(currFile);
        List<Corner> currCorners = currCard.getCorners();

        //CornerLocationEnum counters
        int tlCount = 0;
        int trCount = 0;
        int brCount = 0;
        int blCount = 0;

        for (Corner corner: currCorners) {

            if (corner.getLocation() == CornerLocationEnum.TOP_LEFT) tlCount++;
            if (corner.getLocation() == CornerLocationEnum.TOP_RIGHT) trCount++;
            if (corner.getLocation() == CornerLocationEnum.BOTTOM_RIGHT) brCount++;
            if (corner.getLocation() == CornerLocationEnum.BOTTOM_LEFT) blCount++;

        }
        if (!(trCount == 2 && tlCount == 2 && brCount == 2 && blCount == 2 )) fail("CornerLocationEnum counters are not equals to 2");

    }


    //TODO: add this test for all card type
    //Test to validate all ResourceCard's corners for every ResourceCard in 'resources/assets/resourcecards'
    @Test
    @DisplayName("Validate that all corners and their attributes in resourceCards are not null")
    public void validateNotNullAllCornersResourceCards() throws FileNotFoundException {

        //                                  curr = current
        ResourceCard currCard;
        List<Corner> currCorners;
        File currFile;

        for (int i = 2; i <= numOfResourceCards; i++) {

            //Card selection
            currFile = Paths.get("src/main/resources/assets/resourcecards/testCard"+i+".json").toFile();
            currCard = new ResourceCard(currFile);

            //Corners validation
            currCorners = currCard.getCorners();

            for (Corner corner: currCorners) {

                if(corner == null) {
                    fail("corner is null: card "+i);
                }

                //null attributes tests
                if (corner.getLocation() == null) {
                    fail("corner location is null: card "+i);

                }
                if (corner.getFace() == null) {
                    fail("corner face is null: card "+i);

                }
                if ((corner.getObject() == null) && (corner.getResource() == null)) {
                    fail("corner object is null: card "+i);

                }

            }

        }
    }
}
