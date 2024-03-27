package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;

public class RMIClientConnection extends ClientConnection {

    private final RMIClientInterface clientInterface;

    public RMIClientConnection(RMIClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    @Override
    public void receivePacket(Packet packet) {
        try {
            clientInterface.receivePacket(packet);
        } catch (RemoteException e) {
            System.err.println("Error sending packet to client " + e);
        }
    }
}
