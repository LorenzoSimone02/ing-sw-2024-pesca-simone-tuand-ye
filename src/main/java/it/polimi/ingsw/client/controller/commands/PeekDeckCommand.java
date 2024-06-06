package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.PeekDeckPacket;

public class PeekDeckCommand extends Command {

    public PeekDeckCommand() {
        commandName = "/peekDeck";
        description = "  Peek the card on top of Resource/Gold deck\n  Usage: /peekDeck <'resource' or 'gold'>";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {

        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String deckChoice = input.split(" ")[0];

                if (deckChoice.equalsIgnoreCase("resource")) {
                    clientManager.getNetworkHandler().sendPacket(new PeekDeckPacket(false));
                } else if (deckChoice.equalsIgnoreCase("gold")) {
                    clientManager.getNetworkHandler().sendPacket(new PeekDeckPacket(true));
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

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
