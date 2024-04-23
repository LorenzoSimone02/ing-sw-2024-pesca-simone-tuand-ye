package it.polimi.ingsw.server.model.objectives;

import it.polimi.ingsw.server.model.card.CardColorEnum;
import it.polimi.ingsw.server.model.objectives.strategies.*;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.util.List;

public enum ObjectiveType {

    BOTTOM_LEFT_L_SHAPE(new BottomLeftLShape(CardColorEnum.GREEN, CardColorEnum.PURPLE, 3)),
    BOTTOM_RIGHT_L_SHAPE(new BottomRightLShape(CardColorEnum.RED, CardColorEnum.GREEN, 3)),
    TOP_LEFT_L_SHAPE(new TopLeftLShape(CardColorEnum.PURPLE, CardColorEnum.BLUE, 3)),
    TOP_RIGHT_L_SHAPE(new TopRightLShape(CardColorEnum.BLUE, CardColorEnum.RED, 3)),
    TOP_RIGHT_DIAGONAL_RED(new TopRightDiagonal(CardColorEnum.RED, 2)),
    TOP_RIGHT_DIAGONAL_BLUE(new TopRightDiagonal(CardColorEnum.BLUE, 2)),
    TOP_LEFT_DIAGONAL_GREEN(new TopLeftDiagonal(CardColorEnum.GREEN, 2)),
    TOP_LEFT_DIAGONAL_PURPLE(new TopLeftDiagonal(CardColorEnum.PURPLE, 2)),
    RESOURCE_FUNGI(new ResourceStrategy(ResourceTypeEnum.FUNGI, 3, 2)),
    RESOURCE_PLANT(new ResourceStrategy(ResourceTypeEnum.PLANT, 3, 2)),
    RESOURCE_ANIMAL(new ResourceStrategy(ResourceTypeEnum.ANIMAL, 3, 2)),
    RESOURCE_INSECT(new ResourceStrategy(ResourceTypeEnum.INSECT, 3, 2)),
    OBJECT_QUILL_INKWELL_MANUSCRIPT(new ObjectStrategy(List.of(ObjectTypeEnum.QUILL, ObjectTypeEnum.INKWELL, ObjectTypeEnum.MANUSCRIPT),3)),
    OBJECT_MANUSCRIPT(new ObjectStrategy(List.of(ObjectTypeEnum.MANUSCRIPT, ObjectTypeEnum.MANUSCRIPT), 2)),
    OBJECT_INKWELL(new ObjectStrategy(List.of(ObjectTypeEnum.INKWELL, ObjectTypeEnum.INKWELL), 2)),
    OBJECT_QUILL(new ObjectStrategy(List.of(ObjectTypeEnum.QUILL, ObjectTypeEnum.QUILL), 2));

    private final ObjectiveStrategy strategy;

    ObjectiveType(ObjectiveStrategy strategy) {
        this.strategy = strategy;
    }

    public ObjectiveStrategy getStrategy() {
        return strategy;
    }
}
