package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;

import java.util.HashMap;

public class HelpCommand extends Command {

    private final HashMap<String, Command> commandMap;

    public HelpCommand(HashMap<String, Command> commandMap){
        commandName = "/help";
        description = "  Prints the list of available commands and their description";

        this.commandMap = commandMap;
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        System.out.println("Available commands:");
        for(Command command : commandMap.values()){
            System.out.println(command.getCommandName());
            System.out.println(command.getDescription());
        }
    }
}

