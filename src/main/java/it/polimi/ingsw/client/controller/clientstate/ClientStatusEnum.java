package it.polimi.ingsw.client.controller.clientstate;

/**
 * The enum represents all the possible states of a client in the game
 */
public enum ClientStatusEnum {

    DISCONNECTED,
    LOBBY,
    LOGGED,
    CONNECTED,
    CHOOSING_COLOR,
    CHOOSING_STARTER_FACE,
    CHOOSING_OBJECTIVE,
    PLAYING,
    LAST_TURN,
    ENDED,
    ERROR
}
