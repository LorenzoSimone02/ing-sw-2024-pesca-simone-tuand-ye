package it.polimi.ingsw.client.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientStatusEnum;
import it.polimi.ingsw.network.packets.LoginRequestPacket;

public class LoginCommand extends Command {

    public LoginCommand() {
        setCommand("/login");
        setDescription("  Login into a game \n  Usage: /login <username>");
        addValidStatus(ClientStatusEnum.LOGIN);
    }


    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExectuable()) {
            String username = input.split(" ")[0];
            if (username.matches("[a-zA-Z0-9]+") && input.split(" ").length == 1) {
                System.out.println("Username choosen: " + username);
                LoginRequestPacket loginRequestPacket = new LoginRequestPacket(username);
                clientManager.getNetworkHandler().sendPacket(loginRequestPacket);
            } else {
                System.err.println("That username is not valid, please choose another one using only letters and numbers.");
            }
        }
    }
}
