package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.LoginPacket;

/**
 * The class represents the command that logs a player in
 */
public class LoginCommand extends Command {

    /**
     * The constructor of the class
     */
    public LoginCommand() {
        commandName = "/login";
        description = "  Sets your Username \n  Usage: /login <username>";
        addValidStatus(ClientStatusEnum.LOBBY);
    }

    /**
     * The method executes the command that logs a player in
     * @param input the username
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String username = input.split(" ")[0];
            if (username.matches("[a-zA-Z0-9]+") && input.split(" ").length == 1) {
                LoginPacket loginRequestPacket = new LoginPacket(username);
                clientManager.getGameState().setLastPing(System.currentTimeMillis());
                clientManager.getNetworkHandler().sendPacket(loginRequestPacket);
            } else {
                System.err.println("That username is not valid, please choose another one using only letters and numbers.");
            }
        } else {
            System.err.println("You can't login now.");
        }
    }

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
