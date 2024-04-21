package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientStatusEnum;
import it.polimi.ingsw.server.model.game.GameStatusEnum;

import java.util.HashMap;

public class HelpCommand extends Command {

    private final HashMap<String, Command> commandMap;

    public HelpCommand(HashMap<String, Command> commandMap){
        setCommand("/help");
        setDescription("  Prints the list of available commands and their description");

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

