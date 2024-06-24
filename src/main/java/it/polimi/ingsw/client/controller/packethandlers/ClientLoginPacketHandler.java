package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The class that handles the client login packets from the server
 */
public class ClientLoginPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the client login packet
     * @param packet the client login packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        LoginPacket loginPacket = (LoginPacket) packet;
        clientManager.getGameState().setUsername(loginPacket.getUsername());
        clientManager.getGameState().setClientStatus(ClientStatusEnum.LOGGED);
        if (clientManager.getViewMode() == ViewModeEnum.GUI) {
            GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
            guiClient.changeScene(clientManager.getGameState().getClientStatus());
        }
        clientManager.getNetworkHandler().sendPacket(new JoinPacket());
    }
}
