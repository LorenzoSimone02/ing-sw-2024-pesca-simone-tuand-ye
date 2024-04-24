package it.polimi.ingsw.server.model.game;

import java.io.Serializable;

public enum GameStatusEnum implements Serializable {
    WAITING_FOR_PLAYERS,
    STARTING,
    ASSIGNING_STARTER_CARDS,
    CHOOSING_COLOR,
    DRAWING_FIRST_CARDS,
    ASSIGNING_COMMON_OBJECTIVES,
    CHOOSING_PERSONAL_OBJECTIVE,
    ASSIGNING_FIRST_PLAYER,
    PLAYING,
    FINAL_TURNS,
    ENDING,
    ERROR
}
