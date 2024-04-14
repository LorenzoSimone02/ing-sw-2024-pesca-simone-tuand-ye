package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public enum CardColorEnum {

    GREEN(ResourceTypeEnum.PLANT),
    BLUE(ResourceTypeEnum.ANIMAL),
    PURPLE(ResourceTypeEnum.INSECT),
    RED(ResourceTypeEnum.FUNGI),
    GOLD(ResourceTypeEnum.EMPTY);

    private final ResourceTypeEnum resourceType;

    CardColorEnum(ResourceTypeEnum resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceTypeEnum getResourceType() {
        return resourceType;
    }
}
