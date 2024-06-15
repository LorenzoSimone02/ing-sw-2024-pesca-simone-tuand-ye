package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;

import java.util.ArrayList;

/**
 * The class represents the generic command a client can choose to execute
 */
public abstract class Command {

    /**
     * The name of the command
     */
    String commandName;

    /**
     * The description of the command
     */
    String description;

    /**
     * The list of valid statuses
     */
    final ArrayList<ClientStatusEnum> validStatuses = new ArrayList<>();

    /**
     * The method executes the command
     * @param input the input of the command
     * @param clientManager the client manager
     */
    public abstract void executeCommand(String input, ClientManager clientManager);

    /**
     * The method returns the name of the command
     * @return commandName the name of the command
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * The method returns the description of the command
     * @return description the description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     * The method adds a valid status to the list of valid statuses
     * @param status the status to add
     */
    public void addValidStatus(ClientStatusEnum status) {
        validStatuses.add(status);
    }

    /**
     * The method returns the list of valid statuses
     * @return validStatuses the list of valid statuses
     */
    public ArrayList<ClientStatusEnum> getValidStatuses() {
        return validStatuses;
    }
}
