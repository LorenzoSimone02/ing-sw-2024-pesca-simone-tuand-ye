package it.polimi.ingsw.server.model.player;

/**
 * This class represents a player's token
 */
public record PlayerToken(TokenColorEnum color) {

    /**
     * Returns the color of the token
     * @return the color of the token
     */
    public TokenColorEnum getColor() {
        return color;
    }
}
