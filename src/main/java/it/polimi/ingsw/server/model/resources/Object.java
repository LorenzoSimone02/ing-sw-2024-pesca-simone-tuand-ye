package it.polimi.ingsw.server.model.resources;

/**
 * This class represents an object in the game
 */
public record Object(ObjectTypeEnum type) {

    /**
     * Returns the type of the object
     * @return the type of the object
     */
    public ObjectTypeEnum getType() {
        return type;
    }
}
