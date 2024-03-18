package it.polimi.ingsw.server.model.resources;

public class Object {
    private final ObjectTypeEnum type;

    public Object(ObjectTypeEnum type) {
        this.type = type;
    }

    public ObjectTypeEnum getType() {
        return type;
    }
}
