package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

public enum GoldStrategyType {

    OBJECT_COUNT_INKWELL(new ObjectCount(1, ObjectTypeEnum.INKWELL)),
    OBJECT_COUNT_MANUSCRIPT(new ObjectCount(1, ObjectTypeEnum.MANUSCRIPT)),
    OBJECT_COUNT_QUILL(new ObjectCount(1, ObjectTypeEnum.QUILL)),
    CORNER_COUNT(new CornerCount(2)),
    PLAIN_POINTS_3(new PlainPoints(3)),
    PLAIN_POINTS_5(new PlainPoints(5));

    private final GoldStrategy strategy;

    GoldStrategyType(GoldStrategy strategy) {
        this.strategy = strategy;
    }

    public GoldStrategy getStrategy() {
        return strategy;
    }

}
