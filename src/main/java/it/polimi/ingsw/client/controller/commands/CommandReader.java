package it.polimi.ingsw.client.controller.commands;

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
        loadCommands();

        while (!Thread.currentThread().isInterrupted()) {
            String command = sc.nextLine().trim();
            evaluateCommand(command);
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

    private void loadCommands() {
        this.inputCommandMap = new HashMap<>();

        this.inputCommandMap.put("/help", new HelpCommand(this.inputCommandMap));
        this.inputCommandMap.put("/login", new LoginCommand());
        this.inputCommandMap.put("/playersNumber", new PlayersNumberCommand());
        this.inputCommandMap.put("/chat", new ChatCommand());
    }
}
