package it.polimi.ingsw.server.model.resources;

public record Object(ObjectTypeEnum type) {

    public ObjectTypeEnum getType() {
        return type;
    }
}
