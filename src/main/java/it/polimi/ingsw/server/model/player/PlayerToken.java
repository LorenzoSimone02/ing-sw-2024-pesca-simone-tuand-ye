package it.polimi.ingsw.server.model.player;

public record PlayerToken(TokenColorEnum color) {

    public TokenColorEnum getColor() {
        return color;
    }
}
