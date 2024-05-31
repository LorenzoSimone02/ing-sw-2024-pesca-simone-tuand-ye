package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientNetworkHandler extends UnicastRemoteObject {

    private ClientManager clientManager;

    public ClientNetworkHandler() throws RemoteException {
        super();
    }

    public void sendPacket(Packet packet) {
        long time = System.currentTimeMillis() - clientManager.getGameState().getLastPing();
        if (time > 7000) {
            System.err.println("Lost connection with the Server");
            clientManager.getGameState().setClientStatus(ClientStatusEnum.DISCONNECTED);
        }
        packet.setSender(clientManager.getGameState().getUuid());
    }

    public void receivePacket(Packet packet) {
        if (packet.getClientPacketHandler() != null) {
            clientManager.getGameState().setLastPing(System.currentTimeMillis());
            packet.getClientPacketHandler().handlePacket(packet, clientManager);
        } else {
            System.err.println("Received an unsupported packet");
        }
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
}
