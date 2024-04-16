package it.polimi.ingsw.network.packets;

public class PingResponsePacket extends Packet {

    public PingResponsePacket(String sender) {
        this.sender = sender;
    }

    @Override
    public void handle() {
        System.out.println("Ping response packet received by " + getSender());
    }
}
