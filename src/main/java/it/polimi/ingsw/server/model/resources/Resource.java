package it.polimi.ingsw.server.model.resources;

public class Resource {
    private final ResourceTypeEnum type;

    public Resource(ResourceTypeEnum type){
        this.type = type;
    }
    public ResourceTypeEnum getType() {
        return type;
    }
}
