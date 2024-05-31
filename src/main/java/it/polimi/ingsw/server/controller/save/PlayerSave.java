package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;

public class PlayerSave implements Serializable {

    private final String username;
    private final int score;
    private final String token;
    private final CardSave starterCard;
    private final CardSave objectiveCard;

    public PlayerSave(Player player) {
        this.username = player.getUsername();
        this.score = player.getScore();
        this.token = player.getToken().toString();
        this.starterCard = new CardSave(player.getStarterCard());
        this.objectiveCard = new CardSave(player.getObjectiveCard());
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getToken() {
        return token;
    }

    public CardSave getStarterCard() {
        return starterCard;
    }

    public CardSave getObjectiveCard() {
        return objectiveCard;
    }
}
