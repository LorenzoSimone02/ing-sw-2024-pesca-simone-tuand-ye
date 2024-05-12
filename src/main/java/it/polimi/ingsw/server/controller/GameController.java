package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.ConnectionEventPacket;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.GameStartedPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.server.controller.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.controller.exceptions.FullLobbyException;
import it.polimi.ingsw.server.controller.exceptions.GameStartException;
import it.polimi.ingsw.server.controller.exceptions.IllegalOperationForStateException;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.*;

public class GameController {

    private Game game;
    private final ArrayList<PlayerController> playerControllers;
    private final ServerNetworkHandler networkHandler;

    public GameController(ServerNetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
        this.playerControllers = new ArrayList<>();
    }

    public Game getGame() {
        return game;
    }

    public ServerNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public synchronized PlayerController getPlayerController(Player player) {
        for (PlayerController controller : playerControllers) {
            if (controller.getPlayer().equals(player)) {
                return controller;
            }
        }
        return null;
    }

    public synchronized PlayerController getPlayerController(String nickname) {
        for (PlayerController controller : playerControllers) {
            if (controller.getPlayer().getUsername().equals(nickname)) {
                return controller;
            }
        }
        return null;
    }

    public synchronized void setMaxPlayers(int playersNumber) {
        game.getInfo().setMaxPlayers(playersNumber);
    }

    public synchronized Player addPlayer(String username) throws DuplicatePlayerException, FullLobbyException {
        if (hasDisconnected(username)) {
            return reconnectPlayer(username);
        }
        if (game.getInfo().getGameStatus() != GameStatusEnum.WAITING_FOR_PLAYERS) {
            throw new IllegalOperationForStateException(game.getInfo().getGameStatus());
        }
        if (getPlayerByNick(username).isPresent()) {
            throw new DuplicatePlayerException(username);
        }
        if (game.getPlayers().size() >= game.getInfo().getMaxPlayers()) {
            throw new FullLobbyException();
        }

        Player player = new Player(username, game);
        PlayerController controller = new PlayerController(player);
        playerControllers.add(controller);
        game.getPlayers().add(player);
        game.getInfo().setPlayersNumber(game.getPlayers().size());

        return player;
    }

    public Player reconnectPlayer(String player) {
        Iterator<Player> iterator = game.getOfflinePlayers().iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getUsername().equalsIgnoreCase(player)) {
                game.getPlayers().add(p);
                iterator.remove();
                return p;
            }
        }
        return null;
    }

    public synchronized void onDisconnect(String username) {
        Optional<Player> player = getPlayerByNick(username);
        if (player.isPresent()) {
            if (game.getInfo().getGameStatus().equals(GameStatusEnum.PLAYING)) {
                game.getOfflinePlayers().add(player.get());
            }
            removePlayer(username);
            networkHandler.sendPacketToAll(new ConnectionEventPacket(username, true, false));
        }
    }

    public synchronized void removePlayer(String player) {
        if (game == null) return;
        for (Player players : game.getPlayers()) {
            if (players.getUsername().equals(player)) {
                playerControllers.remove(getPlayerController(players));
                game.getPlayers().remove(players);
                game.getInfo().setPlayersNumber(game.getPlayers().size());
            }
        }
    }

    public boolean hasDisconnected(String player) {
        if (game == null) return false;
        for (Player players : game.getOfflinePlayers()) {
            if (players.getUsername().equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void createGame(int gameId) {
        game = new Game(gameId);
    }

    public synchronized void checkStartCondition() {
        if (game.getPlayers().size() == game.getInfo().getMaxPlayers()) {
            startGame();
        }
    }

    public synchronized void startGame() throws GameStartException {
        try {
            networkHandler.sendPacketToAll(new InfoPacket(Printer.ANSI_GREEN + "\nThe required number of players has been reached. The game is starting.\n" + Printer.ANSI_RESET));
            game.getInfo().setGameStatus(GameStatusEnum.STARTING);

            instantiateCards();
            assignStarterCard();
            assignCommonObjectives();
            chooseFirstPlayer();

            game.getTable().addCardOnGround(game.getTable().getResourceDeck().drawCard());
            game.getTable().addCardOnGround(game.getTable().getResourceDeck().drawCard());
            game.getTable().addCardOnGround(game.getTable().getGoldDeck().drawCard());
            game.getTable().addCardOnGround(game.getTable().getGoldDeck().drawCard());

            for (Player p : game.getPlayers()) {
                p.addCardInHand(game.getTable().getResourceDeck().drawCard());
                p.addCardInHand(game.getTable().getResourceDeck().drawCard());
                p.addCardInHand(game.getTable().getGoldDeck().drawCard());
            }

            GameStartedPacket gameStartedPacket = new GameStartedPacket(game);
            networkHandler.sendPacketToAll(gameStartedPacket);

            saveGameToFile();
            game.getInfo().setGameStatus(GameStatusEnum.PLAYING);
        } catch (Exception e) {
            game.getInfo().setGameStatus(GameStatusEnum.ERROR);
            System.err.println(e.getMessage());
            throw new GameStartException();
        }
    }

    public synchronized void loadGameFromFile(GameSave save) {
        createGame(save.getId());
        //TODO: load game
    }

    public synchronized void saveGameToFile() {
        try {
            GameSave save = new GameSave(game);
            File savesDir = Paths.get("src/main/resources/saves").toFile();
            savesDir.mkdir();
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/saves/game" + game.getInfo().getId() + ".save");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(save);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Error while saving the game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void assignStarterCard() {
        for (Player player : game.getPlayers()) {
            StarterCard starterCard = (StarterCard) game.getTable().getStarterDeck().drawCard();
            player.setStarterCard(starterCard);
        }
    }

    public synchronized void assignCommonObjectives() {
        game.addObjectiveCard((ObjectiveCard) game.getTable().getObjectiveDeck().drawCard());
        game.addObjectiveCard((ObjectiveCard) game.getTable().getObjectiveDeck().drawCard());
    }

    public synchronized void instantiateCards() {
        File folder = Paths.get("src/main/resources/assets/resourcecards").toFile();
        Deck resourceDeck = new Deck();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ResourceCard card = new ResourceCard(file);
            resourceDeck.addCard(card);
        }
        resourceDeck.shuffleDeck();
        game.getTable().setResouceDeck(resourceDeck);

        folder = Paths.get("src/main/resources/assets/goldcards").toFile();
        Deck goldDeck = new Deck();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            GoldCard card = new GoldCard(file);
            goldDeck.addCard(card);
        }
        goldDeck.shuffleDeck();
        game.getTable().setGoldDeck(goldDeck);

        folder = Paths.get("src/main/resources/assets/startercards").toFile();
        Deck starterDeck = new Deck();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            StarterCard card = new StarterCard(file);
            starterDeck.addCard(card);
        }
        starterDeck.shuffleDeck();
        game.getTable().setStarterDeck(starterDeck);

        folder = Paths.get("src/main/resources/assets/objectivecards").toFile();
        Deck objectiveDeck = new Deck();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ObjectiveCard card = new ObjectiveCard(file);
            objectiveDeck.addCard(card);
        }
        objectiveDeck.shuffleDeck();
        game.getTable().setObjectiveDeck(objectiveDeck);
    }

    public synchronized void nextTurn() {
        checkEndCondition();
        Player next = nextPlayer();
        game.getInfo().setActivePlayer(next);
        networkHandler.sendPacketToAll(new EndTurnPacket(next.getUsername()));
    }

    public synchronized Player nextPlayer() {
        int index = game.getPlayers().indexOf(game.getInfo().getActivePlayer());
        return game.getPlayers().get((index + 1) % game.getPlayers().size());
    }

    public synchronized void chooseFirstPlayer() {
        Player first = game.getPlayers().get(new Random().nextInt(game.getPlayers().size()));
        game.getInfo().setFirstPlayer(first);
        game.getInfo().setActivePlayer(first);
        networkHandler.sendPacket(networkHandler.getConnectionByNickname(first.getUsername()), new InfoPacket(Printer.ANSI_CYAN + "You have been selected as the first Player, it's your turn." + Printer.ANSI_RESET));
    }

    public synchronized void checkEndCondition() {
        if (game.getPlayers().stream().anyMatch(player -> player.getScore() >= 20) || (game.getTable().getCardsOnGround().isEmpty() && game.getTable().getResourceDeck().getCards().isEmpty() && game.getTable().getGoldDeck().getCards().isEmpty())) {
            endGame();
        }
    }

    public synchronized void endGame() {

        //Additional turns
        int firstPlayerIndex = game.getPlayers().indexOf(game.getInfo().getFirstPlayer());
        Player lastPlayer = game.getPlayers().get(firstPlayerIndex != 0 ? firstPlayerIndex - 1 : game.getInfo().getPlayersNumber() - 1);
        Player next = nextPlayer();

        //TODO: Wait till the players played their additional turn
        while(!(game.getInfo().getActivePlayer().equals(lastPlayer) ) ) {

            game.getInfo().setActivePlayer(next);
            networkHandler.sendPacketToAll(new EndTurnPacket(next.getUsername()));
            next = nextPlayer();
        }


        for (Player p : game.getPlayers()) {
            int secretObjectivePoints = p.getObjectiveCard().calculatePoints(p);
            int publicObjectivePoints = 0;
            for (ObjectiveCard card : game.getObjectiveCards()) {
                publicObjectivePoints += card.calculatePoints(p);
            }
            p.setScore(secretObjectivePoints + publicObjectivePoints);
        }

        //TODO: Handle the case in which there is a tie
        Player winner = game.getPlayers().stream().max((p1, p2) -> Integer.max(p1.getScore(), p2.getScore())).get();
        game.getInfo().setWinner(winner);
        networkHandler.sendPacketToAll(new InfoPacket("The winner is " + winner.getUsername() + "!"));
    }

    public synchronized Optional<Player> getPlayerByNick(String nick) {
        if (game == null) return Optional.empty();
        for (Player p : game.getPlayers()) {
            if (p.getUsername().equalsIgnoreCase(nick)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
