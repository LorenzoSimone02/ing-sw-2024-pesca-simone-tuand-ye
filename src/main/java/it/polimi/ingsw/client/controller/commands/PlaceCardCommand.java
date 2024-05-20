package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class PlaceCardCommand extends Command {

    public PlaceCardCommand() {
        commandName = "/placeCard";
        description = "  Place a Card \n  Usage: /placeCard <id> <x> <y>";
        addValidStatus(ClientStatusEnum.PLAYING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager) && clientManager.getGameState().getCardsInHand().size() == 3) {
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

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
