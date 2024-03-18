package it.polimi.ingsw.server.model.player;

public class PlayerToken {
    private final TokenColorEnum color;

    public PlayerToken(TokenColorEnum color) {
        this.color = color;
    }
    public TokenColorEnum getColor() {
        return color;
    }
}
