package it.polimi.ingsw.server.model.card.corner;

import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

/**
 * The class Corner represents a corner of a card in the game
 */
public class Corner {

    /**
     * The resource contained in the corner
     * */
    private final Resource resource;

    /**
     * The object contained in the corner
     */
    private final Object object;

    /**
     * A boolean that indicates if the corner is visible or not
     */
    private boolean visible;

    /**
     * The face of the card where the corner is located
     */
    private final FaceEnum face;

    /**
     * The location of the corner
     */
    private final CornerLocationEnum location;

    /**
     * Constructor of the class
     * @param location the location of the corner
     * @param resource the resource contained in the corner
     * @param object the object contained in the corner
     * @param face the face of the card where the corner is located
     * @param visible a boolean that indicates if the corner is visible or not
     */
    public Corner(CornerLocationEnum location, Resource resource, Object object, FaceEnum face, boolean visible) {
        this.location = location;
        this.resource = resource;
        this.object = object;
        this.face = face;
        this.visible = visible;
    }

    /**
     * The method returns the resource contained in the corner if the corner is visible, otherwise it returns an empty resource
     * @return the resource contained in the corner if the corner is visible, otherwise it returns an empty resource
     */
    public Resource getResource() {
        return isVisible() && resource != null ? resource : new Resource(ResourceTypeEnum.EMPTY);
    }

    /**
     * The method returns the object contained in the corner if the corner is visible, otherwise it returns an empty object
     * @return the object contained in the corner if the corner is visible, otherwise it returns an empty object
     */
    public Object getObject() {
        return isVisible() && object != null ? object : new Object(ObjectTypeEnum.EMPTY);
    }

    /**
     * The method returns a boolean that indicates if the corner is visible or not
     * @return a boolean that indicates if the corner is visible or not
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * The method sets the visibility of the corner
     * @param visible the visibility of the corner
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * The method returns the face of the card where the corner is located
     * @return the face of the card where the corner is located
     */
    public FaceEnum getFace() {
        return face;
    }

    /**
     * The method returns the location of the corner
     * @return the location of the corner
     */
    public CornerLocationEnum getLocation() {
        return location;
    }

}
