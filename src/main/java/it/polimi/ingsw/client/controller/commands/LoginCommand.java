package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.LoginPacket;

public class LoginCommand extends Command {

    public LoginCommand() {
        commandName = "/login";
        description = "  Login into the current game \n  Usage: /login <username>";
        addValidStatus(ClientStatusEnum.LOGIN);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            String username = input.split(" ")[0];
            if (username.matches("[a-zA-Z0-9]+") && input.split(" ").length == 1) {
                LoginPacket loginRequestPacket = new LoginPacket(username);
                clientManager.getNetworkHandler().sendPacket(loginRequestPacket);
            } else {
                System.err.println("That username is not valid, please choose another one using only letters and numbers.");
            }
        } else {
            System.err.println("Have already logged in.");
        }
    }

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
