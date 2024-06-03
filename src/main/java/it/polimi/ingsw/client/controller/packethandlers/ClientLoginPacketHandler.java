package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientLoginPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        LoginPacket loginPacket = (LoginPacket) packet;
        clientManager.getGameState().setUsername(loginPacket.getUsername());
        clientManager.getGameState().setClientStatus(ClientStatusEnum.LOGGED);
        if (clientManager.getViewMode() == ViewModeEnum.GUI) {
            GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
            guiClient.updateScene(clientManager.getGameState().getClientStatus());
        }
        new Thread(() -> clientManager.getNetworkHandler().sendPacket(new JoinPacket())).start();
    }
}
