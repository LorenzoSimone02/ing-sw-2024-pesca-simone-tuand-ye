package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPeekDeckPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class PeekDeckPacket extends Packet {

    private final int topDeckCardId;
    private final boolean gold;

    public PeekDeckPacket(int topDeckCardId, boolean gold){
        this.topDeckCardId = topDeckCardId;
        this.gold = gold;
    }

    public int getTopDeckCardId(){
        return topDeckCardId;
    }

    public boolean isGold(){
        return gold;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPeekDeckPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}
