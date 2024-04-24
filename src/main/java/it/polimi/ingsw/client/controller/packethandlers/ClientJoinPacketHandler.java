package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientJoinPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        JoinPacket joinPacket = (JoinPacket) packet;
        int gameID = joinPacket.getGameID();

        try {
            ClientNetworkHandler newHandler = clientManager.getNetworkHandler() instanceof RMIClient ? new RMIClient("Game" + gameID, 1099 + gameID) : new SocketClient("localhost", 5000 + gameID);
            clientManager.setNetworkHandler(newHandler);
            clientManager.getGameState().setClientStatus(ClientStatusEnum.LOGIN);
            clientManager.getGameState().setGameID(joinPacket.getGameID());

            System.out.println("You have joined a Game! Now use /login <username> to login.");
        } catch (NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
