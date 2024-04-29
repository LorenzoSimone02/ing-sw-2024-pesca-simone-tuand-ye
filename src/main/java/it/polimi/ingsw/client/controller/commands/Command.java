package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

import java.util.ArrayList;

public abstract class Command {

    String commandName;
    String description;
    final ArrayList<ClientStatusEnum> validStatuses = new ArrayList<>();

    public abstract void executeCommand(String input, ClientManager clientManager);

    public String getCommandName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

    public void addValidStatus(ClientStatusEnum status) {
        validStatuses.add(status);
    }

    public ArrayList<ClientStatusEnum> getValidStatuses() {
        return validStatuses;
    }
}
