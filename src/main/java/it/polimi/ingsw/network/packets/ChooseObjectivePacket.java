package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseObjectivePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseObjectivePacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ChooseObjectivePacket extends Packet {

    private int cardID1;
    private int cardID2;
    private final int choosenCardID;

    public ChooseObjectivePacket(int cardID1, int cardID2) {
        this.cardID1 = cardID1;
        this.cardID2 = cardID2;
        this.choosenCardID = -1;
    }

    public ChooseObjectivePacket(int choosenCardID) {
        this.choosenCardID = choosenCardID;
    }

    public int getCardID1() {
        return cardID1;
    }

    public int getCardID2() {
        return cardID2;
    }

    public int getChoosenCardID() {
        return choosenCardID;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseObjectivePacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseObjectivePacketHandler();
    }
}
