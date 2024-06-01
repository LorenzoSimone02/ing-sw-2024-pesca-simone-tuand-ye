package it.polimi.ingsw.server.model.card.goldstrategies;

import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

/**
 * This enum represents the different types of GoldCard strategies from which a player can get points
 */
public enum GoldStrategyType {

    OBJECT_COUNT_INKWELL(new ObjectCount(1, ObjectTypeEnum.INKWELL)),
    OBJECT_COUNT_MANUSCRIPT(new ObjectCount(1, ObjectTypeEnum.MANUSCRIPT)),
    OBJECT_COUNT_QUILL(new ObjectCount(1, ObjectTypeEnum.QUILL)),
    CORNER_COUNT(new CornerCount(2)),
    PLAIN_POINTS_3(new PlainPoints(3)),
    PLAIN_POINTS_5(new PlainPoints(5));

    /**
     * The strategy of the GoldCard
     */
    private final GoldStrategy strategy;

    /**
     * Constructor of the class
     * @param strategy the strategy of the GoldCard
     */
    GoldStrategyType(GoldStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * The method returns the strategy of the GoldCard
     * @return the strategy of the GoldCard
     */
    public GoldStrategy getStrategy() {
        return strategy;
    }

}
