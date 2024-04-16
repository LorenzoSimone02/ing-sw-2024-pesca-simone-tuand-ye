package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientNetworkHandler extends UnicastRemoteObject {

    private final String nickname;
    public ClientNetworkHandler() throws RemoteException {
        super();
        this.nickname = "Unknown";
    }

    public void sendPacket(Packet packet) {
        packet.setSender(nickname);
    }

    public void receivePacket(Packet packet) {
        packet.handle();
    }

    public String getNickname() {
        return nickname;
    }
}
