package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.controller.ClientManager;

import java.util.HashMap;
import java.util.Scanner;

public class CommandReader implements Runnable {

    private final ClientManager clientManager;
    private HashMap<String, Command> inputCommandMap;

    public CommandReader(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        this.inputCommandMap = new HashMap<>();
        loadCommands();

        while (!Thread.currentThread().isInterrupted()) {
            String inserted = sc.nextLine().trim();
            evaluateCommand(inserted);
        }
    }

    public void evaluateCommand(String command) {
        String[] arguments = command.split(" ");

        if (inputCommandMap.containsKey(arguments[0])) {
            inputCommandMap.get(arguments[0]).executeCommand(command.substring(arguments[0].length()).trim(), clientManager);
        } else {
            System.out.println("Invalid command, type /help to see the list of available commands");
        }
    }

    public void loadCommands(){
        this.inputCommandMap.put("/help", new HelpCommand(this.inputCommandMap));
        this.inputCommandMap.put("/login", new LoginCommand());
        this.inputCommandMap.put("/playersNumber", new PlayersNumberCommand());
    }
}
