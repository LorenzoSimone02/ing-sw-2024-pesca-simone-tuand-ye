package it.polimi.ingsw.server.model.resources;

public record Resource(ResourceTypeEnum type) {

    public ResourceTypeEnum getType() {
        return type;
    }
}
