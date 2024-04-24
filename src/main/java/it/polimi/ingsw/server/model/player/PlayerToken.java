package it.polimi.ingsw.server.model.player;

import java.io.Serializable;

public record PlayerToken(TokenColorEnum color) implements Serializable {

    public TokenColorEnum getColor() {
        return color;
    }
}
