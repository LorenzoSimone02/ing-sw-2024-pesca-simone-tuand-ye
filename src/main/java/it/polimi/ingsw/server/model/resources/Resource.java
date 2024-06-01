package it.polimi.ingsw.server.model.resources;

/**
 * This class represents a resource in the game
 */
public record Resource(ResourceTypeEnum type) {

    /**
     * Returns the type of the resource
     * @return the type of the resource
     */
    public ResourceTypeEnum getType() {
        return type;
    }
}
