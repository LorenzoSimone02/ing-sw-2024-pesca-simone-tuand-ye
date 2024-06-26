package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;

/**
 * RMIClientConnection is the class that represents the client-side RMI connection
 */
public class RMIClientConnection extends ClientConnection {

    /**
     * The client-side RMI interface
     */
    private final RMIClientInterface clientInterface;

    /**
     * Constructor of the class
     * @param clientInterface the client-side RMI interface
     */
    public RMIClientConnection(RMIClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    /**
     * The method tries to receive a packet from the server
     * @param packet the packet to receive
     */
    @Override
    public void receivePacket(Packet packet) {
        try {
            clientInterface.receivePacket(packet);
        } catch (RemoteException ignored) {
        }
    }
}
