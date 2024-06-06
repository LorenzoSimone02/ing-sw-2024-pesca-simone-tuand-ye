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

        String cmd = isValidCommand(arguments[0]);
        if (cmd != null) {
            inputCommandMap.get(cmd).executeCommand(command.substring(cmd.length()).trim(), clientManager);
        } else {
            System.out.println("Invalid command, type /help to see the list of available commands");
        }
    }

    private String isValidCommand(String command){
        for(String key : inputCommandMap.keySet()) {
            if (command.equalsIgnoreCase(key)) {
                return key;
            }
        }
        return null;
    }

    private void loadCommands() {
        this.inputCommandMap = new HashMap<>();

        this.inputCommandMap.put("/help", new HelpCommand(this.inputCommandMap));
        this.inputCommandMap.put("/login", new LoginCommand());
        this.inputCommandMap.put("/createGame", new CreateGameCommand());
        this.inputCommandMap.put("/chat", new ChatCommand());
        this.inputCommandMap.put("/scores", new ScoresCommand());
        this.inputCommandMap.put("/join", new JoinCommand());
        this.inputCommandMap.put("/quit", new QuitCommand());
        this.inputCommandMap.put("/turnCard", new TurnCardCommand());
        this.inputCommandMap.put("/chooseColor", new ChooseColorCommand());
        this.inputCommandMap.put("/chooseObjective", new ChooseObjectiveCommand());
        this.inputCommandMap.put("/drawCard", new DrawCardCommand());
        this.inputCommandMap.put("/placeCard", new PlaceCardCommand());
        this.inputCommandMap.put("/showCards", new ShowCardsCommand());
        this.inputCommandMap.put("/viewCard", new ViewCardCommand());
        this.inputCommandMap.put("/resources", new ResourcesCommand());
        this.inputCommandMap.put("/chooseStarterFace", new ChooseStarterFaceCommand());
        this.inputCommandMap.put("/peekDeck", new PeekDeckCommand());
    }
}
