package it.polimi.ingsw.client.controller.clientstate;

public class PlayerState {

    private final String username;
    private String color;
    int score;

    public PlayerState(String username) {
        this.username = username;
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
