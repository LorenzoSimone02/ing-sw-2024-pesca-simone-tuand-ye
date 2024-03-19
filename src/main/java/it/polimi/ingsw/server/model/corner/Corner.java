package it.polimi.ingsw.server.model.corner;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.Optional;

public class Corner {
    private final Card card;
    private final Resource resource;
    private final Object object;
    private boolean visible;
    private final CornerLocationEnum location;

    public Corner(CornerLocationEnum location, Card card, Resource resource, Object object, boolean visible) {
        this.location = location;
        this.card = card;
        this.resource = resource;
        this.object = object;
        this.visible = visible;
    }

    public Card getCard() {
        return card;
    }

    public Optional<Resource> getResource() {
        return Optional.ofNullable(resource);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Optional<Object> getObject() {
        return Optional.ofNullable(object);
    }

    public CornerLocationEnum getLocation() {
        return location;
    }

}
