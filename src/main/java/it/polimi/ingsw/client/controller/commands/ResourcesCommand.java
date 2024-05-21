package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public class ResourcesCommand extends Command {

    public ResourcesCommand() {
        commandName = "/resources";
        description = "  Shows your Resources or the ones of another Player" +
                " \n  Usage: /resources <player>";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println(Printer.GREEN + "Your Resources:");
                System.out.println(Printer.RED + clientManager.getGameState().getResources().get(ResourceTypeEnum.FUNGI.name()) + " Fungi");
                System.out.println(Printer.BLUE + clientManager.getGameState().getResources().get(ResourceTypeEnum.ANIMAL.name()) + " Animal");
                System.out.println(Printer.PURPLE + clientManager.getGameState().getResources().get(ResourceTypeEnum.INSECT.name()) + " Insect");
                System.out.println(Printer.GREEN + clientManager.getGameState().getResources().get(ResourceTypeEnum.PLANT.name()) + " Plant");
                System.out.println(Printer.YELLOW + clientManager.getGameState().getResources().get(ObjectTypeEnum.INKWELL.name()) + " Inkwell");
                System.out.println(Printer.YELLOW + clientManager.getGameState().getResources().get(ObjectTypeEnum.MANUSCRIPT.name()) + " Manuscript");
                System.out.println(Printer.YELLOW + clientManager.getGameState().getResources().get(ObjectTypeEnum.QUILL.name()) + " Quill" + Printer.RESET);

            } else {
                System.err.println("Usage: /resources <player>");
            }
        } else {
            System.err.println("You can't view your Resources now.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
