package it.polimi.ingsw.client.view.gui;

/**
 * Class that represents a card in the GUI view
 */
public class Card {

    /**
     * The id of the card
     */
    int id;

    /**
     * The position of the card
     */
    int[] pos;

    /**
     * The face of the card
     */
    String face;

    /**
     * The path to the front of the card
     */
    String pathFront;

    /**
     * The path to the back of the card
     */
    String pathBack;

    /**
     * Constructor of the card
     * @param id the id of the card
     */
    Card(int id) {
        this.id = id;
        this.pos = new int[2];
        this.face = "";
        this.pathFront = "";
        this.pathBack = "";
    }

    /**
     * The method returns the face of the card
     * @return the face of the card
     */
    public String getFace() {
        return face;
    }

    /**
     * The method sets the face of the card
     * @param face the face of the card
     */
    public void setFace(String face) {
        this.face = face;
    }

    /**
     * The method sets the position of the card
     * @param x the coordinate x of the card's position
     * @param y the coordinate y of the card's position
     */
    public void setPos(int x, int y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }

    /**
     * The method returns the coordinate x of the card's position
     * @return the coordinate x of the card's position
     */
    public int getX() {
        return pos[0];
    }

    /**
     * The method returns the coordinate y of the card's position
     * @return the coordinate y of the card's position
     */
    public int getY() {
        return pos[1];
    }

    /**
     * The method returns the path to the front of the card
     * @return the path to the front of the card
     */
    public String getPathFront() {
        String path = "Front";
        if (id >= 0 && id <= 40) {
            path = path + "/resource";
            path = path + "/" + id;
        } else if (id >= 41 && id <= 80) {
            path = path + "/gold";
            path = path + "/" + id;
        } else if (id >= 81 && id <= 86) {
            path = path + "/starter";
            path = path + "/" + id;
        } else if (id >= 87 && id <= 103) {
            path = path + "/objective";
            path = path + "/" + id;
        }
        path = path + ".png";
        return path;
    }

    /**
     * The method returns the path to the back of the card
     * @return the path to the back of the card
     */
    public String getPathBack() {
        String path = "Back";
        if (id >= 0 && id <= 40) {
            path = path + "/resource";
            path = path + "/" + id;
        } else if (id >= 41 && id <= 80) {
            path = path + "/gold";
            path = path + "/" + id;
        } else if (id >= 81 && id <= 86) {
            path = path + "/starter";
            path = path + "/" + id;
        } else if (id >= 87 && id <= 103) {
            path = path + "/objective";
            path = path + "/" + id;
        }
        path = path + ".png";
        return path;
    }
}
