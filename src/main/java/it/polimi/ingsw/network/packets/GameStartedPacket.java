package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientGameStartedPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameStartedPacket is a Packet that contains the information about the start of a game
 */
public class GameStartedPacket extends Packet {

    /**
     * The list of the usernames of the players
     */
    private final ArrayList<String> players;

    /**
     * The map that associates the players' usernames with the IDs of the cards in their hands
     */
    private final HashMap<Integer, String> cardsInHands;

    /**
     * The list of the IDs of the cards on the ground
     */
    private final ArrayList<Integer> cardsOnGround;

    /**
     * The username of the first player
     */
    private final String firstPlayer;

    /**
     * The boolean that indicates if the game is a reconnection
     */
    private final boolean reconnection;

    /**
     * Constructor of the class
     *
     * @param game         the game that has started
     * @param reconnection the boolean that indicates if the game is a reconnection
     */
    public GameStartedPacket(Game game, boolean reconnection) {
        this.players = new ArrayList<>();
        this.cardsInHands = new HashMap<>();
        this.cardsOnGround = new ArrayList<>();
        this.reconnection = reconnection;

        for (Player player : game.getPlayers()) {
            players.add(player.getUsername());
            for (Card card : player.getCardsInHand()) {
                cardsInHands.put(card.getId(), player.getUsername());
            }
        }
        for (Card card : game.getTable().getCardsOnGround()) {
            cardsOnGround.add(card.getId());
        }
        for (Card card : game.getTable().getObjectiveCards()) {
            cardsOnGround.add(card.getId());
        }
        this.firstPlayer = game.getInfo().getFirstPlayer().getUsername();
    }

    /**
     * The method returns the list of the usernames of the players
     *
     * @return the list of the usernames of the players
     */
    public ArrayList<String> getPlayers() {
        return players;
    }

    /**
     * The method returns the username of the first player
     *
     * @return the username of the first player
     */
    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * The method returns the map that associates the players' usernames with the IDs of the cards in their hands
     *
     * @return the map that associates the players' usernames with the IDs of the cards in their hands
     */
    public HashMap<Integer, String> getCardsInHands() {
        return cardsInHands;
    }

    /**
     * The method returns the list of the IDs of the cards on the ground
     *
     * @return the list of the IDs of the cards on the ground
     */
    public ArrayList<Integer> getCardsOnGround() {
        return cardsOnGround;
    }

    /**
     * The method returns the boolean that indicates if the game is a reconnection
     *
     * @return the boolean that indicates if the game is a reconnection
     */
    public boolean isReconnection() {
        return reconnection;
    }

    /**
     * The method returns the client-side game starting packets handler
     *
     * @return the client-side game starting packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientGameStartedPacketHandler();
    }

    /**
     * The method returns the server-side game starting packets handler
     *
     * @return the server-side game starting packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }

}
