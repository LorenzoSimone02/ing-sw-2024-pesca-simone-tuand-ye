package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.server.model.card.Card;

/**
 * The class represents the command that allows a player to turn a Card and change its face
 */
public class TurnCardCommand extends Command {

    /**
     * The constructor of the class
     */
    public TurnCardCommand() {
        commandName = "/turnCard";
        description = "  Turns a Card \n  Usage: /turnCard <1/2/3>";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
    }

    /**
     * The method executes the command that allows a player to turn a Card and change its face
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String arg = input.split(" ")[0];
                Card card;
                switch (arg) {
                    case "1" -> {
                        card = clientManager.getGameState().getCardById(clientManager.getGameState().getCardsInHand().getFirst().getId());
                        clientManager.getNetworkHandler().sendPacket(new TurnCardPacket(card.getId()));
                    }
                    case "2" -> {
                        card = clientManager.getGameState().getCardById(clientManager.getGameState().getCardsInHand().get(1).getId());
                        clientManager.getNetworkHandler().sendPacket(new TurnCardPacket(card.getId()));
                    }
                    case "3" -> {
                        card = clientManager.getGameState().getCardById(clientManager.getGameState().getCardsInHand().get(2).getId());
                        clientManager.getNetworkHandler().sendPacket(new TurnCardPacket(card.getId()));
                    }
                    default -> System.err.println("Usage: /turnCard <1/2/3>");
                }
            } else {
                System.err.println("Usage: /turnCard <1/2/3>");
            }
        } else {
            System.err.println("You can't turn a Card now.");
        }
    }

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return clientManager.getGameState().getActivePlayer().equalsIgnoreCase(clientManager.getGameState().getUsername()) && getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
