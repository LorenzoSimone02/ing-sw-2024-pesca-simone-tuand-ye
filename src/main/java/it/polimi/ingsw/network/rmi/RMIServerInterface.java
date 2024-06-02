package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.packets.Packet;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMIServerInterface is the interface that represents the server-side RMI interface
 */
public interface RMIServerInterface extends Remote {

    /**
     * The method receives a packet from a client if the client is already connected, otherwise it creates a new connection
     * @param packet the packet received
     * @param clientInterface the client-side RMI interface
     * @throws RemoteException if there is an error with the remote connection
     */
    void receivePacket(Packet packet, RMIClientInterface clientInterface) throws RemoteException;

}
