package it.polimi.ingsw.client.view.gui;

public class Card {

    int id;
    int[] pos;
    String face;
    String pathFront;
    String pathBack;

    Card(int id) {
        this.id = id;
        this.pos = new int[2];
        this.face = "";
        this.pathFront = "";
        this.pathBack = "";
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public void setPos(int x, int y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }

    public int getX() {
        return pos[0];
    }

    public int getY() {
        return pos[1];
    }

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
