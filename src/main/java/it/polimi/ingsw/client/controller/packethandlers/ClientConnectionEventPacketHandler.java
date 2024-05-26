package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.packets.ConnectionEventPacket;
import it.polimi.ingsw.network.packets.Packet;
import javafx.application.Platform;

public class ClientConnectionEventPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ConnectionEventPacket connectionEventPacket = (ConnectionEventPacket) packet;
        String username = connectionEventPacket.getUsername();

        if (!username.equals(clientManager.getGameState().getUsername())) {
            clientManager.getGameState().addPlayerState(new PlayerState(username));
        }

        if (clientManager.getViewMode() == ViewModeEnum.GUI && clientManager.getGameState().getClientStatus() == ClientStatusEnum.CONNECTED) {
            GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
            System.out.println(guiClient.getControllersMap().size());
            Platform.runLater(() -> guiClient.getControllersMap().get(ClientStatusEnum.CONNECTED).update());
        }

        if (connectionEventPacket.isDisconnection()) {
            System.out.println(Printer.YELLOW + "Player " + username + " has disconnected from the Game." + Printer.RESET);
        } else if (connectionEventPacket.isReconnection()) {
            System.out.println(Printer.YELLOW + "Player " + username + " has reconnected to the Game." + Printer.RESET);
        } else {
            System.out.println(Printer.YELLOW + "Player " + username + " has joined the Game." + Printer.RESET);
        }
    }
}
