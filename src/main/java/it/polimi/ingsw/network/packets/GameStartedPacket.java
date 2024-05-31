package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientGameStartedPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStartedPacket extends Packet {

    private final int gameID;
    private final ArrayList<String> players;
    private final HashMap<Integer, String> cardsInHands;
    private final ArrayList<Integer> cardsOnGround;
    private final String firstPlayer;

    public GameStartedPacket(Game game) {
        this.gameID = game.getInfo().getId();
        this.players = new ArrayList<>();
        this.cardsInHands = new HashMap<>();
        this.cardsOnGround = new ArrayList<>();

        for (Player player : game.getPlayers()) {
            players.add(player.getUsername());
            for (Card card : player.getCardsInHand()) {
                cardsInHands.put(card.getId(), player.getUsername());
            }
        }
        for (Card card : game.getTable().getCardsOnGround()) {
            cardsOnGround.add(card.getId());
        }
        for(Card card : game.getTable().getObjectiveCards()){
            cardsOnGround.add(card.getId());
        }
        this.firstPlayer = game.getInfo().getFirstPlayer().getUsername();
    }

    public int getGameID() {
        return gameID;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public HashMap<Integer, String> getCardsInHands() {
        return cardsInHands;
    }

    public ArrayList<Integer> getCardsOnGround() {
        return cardsOnGround;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientGameStartedPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}
