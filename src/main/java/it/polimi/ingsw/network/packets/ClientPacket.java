package it.polimi.ingsw.network.packets;

/**
 * Class that represents a packet sent exclusively by a client
 */
public class ClientPacket extends Packet {
    private String sender;

    public ClientPacket() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    @Override
    public void handle() {

    }
}
