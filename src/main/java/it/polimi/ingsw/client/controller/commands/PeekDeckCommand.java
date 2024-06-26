package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

/**
 * The class represents the command that allows a player to peek the card on top of Resource/Gold deck
 */
public class PeekDeckCommand extends Command {

    /**
     * The constructor of the class
     */
    public PeekDeckCommand() {
        commandName = "/peekDeck";
        description = "  Peek the card on top of Resource/Gold deck\n  Usage: /peekDeck <'resource' or 'gold'>";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
    }

    /**
     * The method executes the command that allows a player to peek the card on top of Resource/Gold deck
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {

        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String deckChoice = input.split(" ")[0];

                if (deckChoice.equalsIgnoreCase("resource")) {
                    Printer.printCard(clientManager.getGameState().getTopResourcesDeckCard());
                } else if (deckChoice.equalsIgnoreCase("gold")) {
                    Printer.printCard(clientManager.getGameState().getTopGoldDeckCard());
                } else {
                    System.err.println("Invalid choice. Type 'gold' or 'resource'");
                }
            } else {
                System.err.println("Usage: /peekDeck <'resource' or 'gold'>");
            }
        } else {
            System.err.println("You can't peek Resource/Gold deck now.");
        }
    }

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
