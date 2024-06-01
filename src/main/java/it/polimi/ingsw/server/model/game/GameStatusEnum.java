package it.polimi.ingsw.server.model.game;

/**
 * This enum represents the different statuses of a game
 */
public enum GameStatusEnum {
    WAITING_FOR_PLAYERS,
    STARTING,
    CHOOSING_COLOR,
    CHOOSING_STARTER_FACE,
    CHOOSING_PERSONAL_OBJECTIVE,
    PLAYING,
    LAST_TURN,
    ENDING,
    ERROR
}
