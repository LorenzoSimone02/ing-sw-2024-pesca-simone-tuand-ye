package it.polimi.ingsw.server.model.player;

public record PlayerToken(TokenColorEnum color) {
    //TODO
    public static final PlayerToken RED = new PlayerToken(TokenColorEnum.RED);
    public static final PlayerToken YELLOW = new PlayerToken(TokenColorEnum.YELLOW);
    public static final PlayerToken GREEN = new PlayerToken(TokenColorEnum.GREEN);
    public static final PlayerToken BLUE = new PlayerToken(TokenColorEnum.BLUE);
    public static final PlayerToken BLACK = new PlayerToken(TokenColorEnum.BLACK);
}
