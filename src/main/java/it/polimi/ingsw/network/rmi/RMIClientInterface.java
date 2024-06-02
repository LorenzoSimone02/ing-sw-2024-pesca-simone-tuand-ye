package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.packets.Packet;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMIClientInterface is the interface that represents the client-side RMI interface
 */
public interface RMIClientInterface extends Remote {

    /**
     * The method sends a packet to the client
     * @param packet the packet to send
     * @throws RemoteException if there is an error with the remote connection
     */
    void sendPacket(Packet packet) throws RemoteException;

    /**
     * The method receives a packet from the server
     * @param packet the packet received
     * @throws RemoteException if there is an error with the remote connection
     */
    void receivePacket(Packet packet) throws RemoteException;

}
