package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientStatusEnum;

import java.util.ArrayList;

public abstract class Command {

    private String commandName;
    private String description;
    private final ArrayList<ClientStatusEnum> validStatuses = new ArrayList<>();

    public abstract void executeCommand(String input, ClientManager clientManager);

    public String getCommandName() {
        return commandName;
    }

    public void setCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addValidStatus(ClientStatusEnum status) {
        validStatuses.add(status);
    }

    public boolean isExecutable() {
        return true;
    }
}
