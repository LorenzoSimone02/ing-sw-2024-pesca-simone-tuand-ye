package it.polimi.ingsw.server.controller.exceptions;

public class IllegalGoldCardPointsException extends RuntimeException{
    public IllegalGoldCardPointsException() {
        super("The given gold card's points were not assigned.");
    }
}
