package it.polimi.ingsw.server.model.card.corner;

import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class Corner {

    private final Resource resource;
    private final Object object;
    private boolean visible;
    private final FaceEnum face;
    private final CornerLocationEnum location;

    public Corner(CornerLocationEnum location, Resource resource, Object object, FaceEnum face, boolean visible) {
        this.location = location;
        this.resource = resource;
        this.object = object;
        this.face = face;
        this.visible = visible;
    }

    public Resource getResource() {
        return isVisible() && resource != null ? resource : new Resource(ResourceTypeEnum.EMPTY);
    }

    public Object getObject() {
        return isVisible() && object != null ? object : new Object(ObjectTypeEnum.EMPTY);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public FaceEnum getFace() {
        return face;
    }

    public CornerLocationEnum getLocation() {
        return location;
    }

}
