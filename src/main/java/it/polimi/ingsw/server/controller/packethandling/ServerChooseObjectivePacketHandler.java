package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChooseObjectivePacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

public class ServerChooseObjectivePacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChooseObjectivePacket chooseObjectivePacket = (ChooseObjectivePacket) packet;
        int chosenCardID = chooseObjectivePacket.getChoosenCardID();
        if(chosenCardID > 0){
            //TODO: Get card from ID
            controller.getNetworkHandler().sendPacket(clientConnection, chooseObjectivePacket);
        }
    }
}
