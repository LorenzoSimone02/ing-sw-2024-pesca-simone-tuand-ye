package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.packets.ClientPacket;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {

    void receivePacket(ClientPacket packet, RMIClientInterface clientInterface) throws RemoteException;

}
