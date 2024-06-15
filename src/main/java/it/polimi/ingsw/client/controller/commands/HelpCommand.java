package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;

import java.util.HashMap;

/**
 * The class represents the command that prints the list of available commands and their description
 */
public class HelpCommand extends Command {

    /**
     * The map of commands
     */
    private final HashMap<String, Command> commandMap;

    /**
     * The constructor of the class
     * @param commandMap the map of commands
     */
    public HelpCommand(HashMap<String, Command> commandMap){
        commandName = "/help";
        description = "  Prints the list of available commands and their description";

        this.commandMap = commandMap;
    }

    /**
     * The method executes the command that prints the list of available commands and their description
     * @param input the input of the command
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        System.out.println("Available commands:");
        for(Command command : commandMap.values()){
            System.out.println(command.getCommandName());
            System.out.println(command.getDescription());
        }
    }
}

