package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The class represents the command reader
 */
public class CommandReader implements Runnable {

    /**
     * The client manager
     */
    private final ClientManager clientManager;

    /**
     * The map of commands
     */
    private HashMap<String, Command> inputCommandMap;

    /**
     * The constructor of the class
     * @param clientManager the client manager
     */
    public CommandReader(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    /**
     * The method runs the command reader
     */
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        loadCommands();

        while (!Thread.currentThread().isInterrupted()) {
            String command = sc.nextLine().trim();
            evaluateCommand(command);
        }
    }

    /**
     * The method evaluates the command and checks if it is valid
     * @param command the command to evaluate
     */
    public void evaluateCommand(String command) {
        String[] arguments = command.split(" ");

        String cmd = isValidCommand(arguments[0]);
        if (cmd != null) {
            inputCommandMap.get(cmd).executeCommand(command.substring(cmd.length()).trim(), clientManager);
        } else {
            System.out.println("Invalid command, type /help to see the list of available commands");
        }
    }

    /**
     * The method checks if the command is valid
     * @param command the command to check
     * @return the command if it is valid, null otherwise
     */
    private String isValidCommand(String command){
        for(String key : inputCommandMap.keySet()) {
            if (command.equalsIgnoreCase(key)) {
                return key;
            }
        }
        return null;
    }

    /**
     * The method loads all the valid commands
     */
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
