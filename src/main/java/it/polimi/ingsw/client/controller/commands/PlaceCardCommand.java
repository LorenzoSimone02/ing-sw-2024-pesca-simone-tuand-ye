package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.model.card.ResourceCard;

/**
 * The class represents the command that allows a player to place a Card
 */
public class PlaceCardCommand extends Command {

    /**
     * The constructor of the class
     */
    public PlaceCardCommand() {
        commandName = "/placeCard";
        description = "  Place a Card \n  Usage: /placeCard <id> <x> <y>";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
    }

    /**
     * The method executes the command that allows a player to place a Card
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String[] split = input.split(" ");
            if (split.length == 3) {
                int id = Integer.parseInt(split[0]);
                if (id < 1 || id > 3) {
                    System.err.println("Invalid id. The id must be between 1 and 3.");
                    return;
                }
                int x = Integer.parseInt(split[1]);
                int y = Integer.parseInt(split[2]);
                ResourceCard card = clientManager.getGameState().getCardsInHand().get(id - 1);
                clientManager.getNetworkHandler().sendPacket(new PlaceCardPacket(card.getId(), x, y));
            } else {
                System.err.println("Usage: /placeCard <id> <x> <y>");
            }
        } else {
            System.err.println("You can't place a Card now.");
        }
    }

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus()) && clientManager.getGameState().getCardsInHand().size() == 3;
    }
}
