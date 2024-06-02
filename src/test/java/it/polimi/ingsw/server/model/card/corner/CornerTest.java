package it.polimi.ingsw.server.model.card.corner;

import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CornerTest {

    ResourceCard testCard;

    @BeforeEach
    public void setUp() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard2.json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            testCard = new ResourceCard(jsonData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Validate that corners in a single resource card have 4x2 different CornerLocationEnum")
    public void validateAllCornersLocation() {
        List<Corner> currCorners = testCard.getCorners();

        //CornerLocationEnum counters
        int tlCount = 0;
        int trCount = 0;
        int brCount = 0;
        int blCount = 0;

        for (Corner corner : currCorners) {

            if (corner.getLocation() == CornerLocationEnum.TOP_LEFT) tlCount++;
            if (corner.getLocation() == CornerLocationEnum.TOP_RIGHT) trCount++;
            if (corner.getLocation() == CornerLocationEnum.BOTTOM_RIGHT) brCount++;
            if (corner.getLocation() == CornerLocationEnum.BOTTOM_LEFT) blCount++;

        }
        if (!(trCount == 2 && tlCount == 2 && brCount == 2 && blCount == 2))
            fail("CornerLocationEnum counters are not equals to 2");
    }

    @Test
    void getResource() {
        ResourceTypeEnum actualResourceType = testCard.getCorners().stream().filter(corner -> corner.getFace().equals(FaceEnum.FRONT)).toList().getFirst().getResource().getType();
        assertEquals(actualResourceType, ResourceTypeEnum.FUNGI);
    }

    @Test
    void getObject() {
        ObjectTypeEnum actualObjectType = testCard.getCorners().stream().filter(corner -> corner.getFace().equals(FaceEnum.FRONT)).toList().getFirst().getObject().getType();
        assertEquals(actualObjectType, ObjectTypeEnum.EMPTY);
    }

    @Test
    void isVisible() {
        assertTrue(testCard.getCorners().getFirst().isVisible());
    }

    @Test
    void setVisible() {
        List<Corner> currCorners = testCard.getCorners();

        boolean prevVisibility = currCorners.getFirst().isVisible();
        currCorners.getFirst().setVisible(!prevVisibility);

        assertEquals(!prevVisibility, currCorners.getFirst().isVisible());
    }

    @Test
    void getFace() {
        FaceEnum actualFace = testCard.getCorners().getFirst().getFace();
        assertEquals(actualFace, FaceEnum.BACK);
    }

    @Test
    void getLocation() {
        CornerLocationEnum actualLocation = testCard.getCorners().getFirst().getLocation();
        CornerLocationEnum actualLocation2 = testCard.getCorners().getLast().getLocation();
        assertEquals(actualLocation, CornerLocationEnum.TOP_LEFT);
        assertEquals(actualLocation2, CornerLocationEnum.BOTTOM_RIGHT);

    }
}
