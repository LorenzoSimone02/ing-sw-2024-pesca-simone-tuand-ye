package it.polimi.ingsw.client.controller.clientstate;

public class PlayerState {

    private final String username;
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
}
