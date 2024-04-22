package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientNetworkHandler extends UnicastRemoteObject {

    private String nickname;
    private ClientManager clientManager;

    public ClientNetworkHandler() throws RemoteException {
        super();
        this.nickname = "Unknown";
    }

    public void sendPacket(Packet packet) {
        packet.setSender(nickname);
    }

    public void receivePacket(Packet packet) {
        if (packet.getClientPacketHandler() != null) {
            packet.getClientPacketHandler().handlePacket(packet, clientManager);
        } else {
            System.err.println("Received an unsupported packet");
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
}
