package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.model.exceptions.FullLobbyException;
import it.polimi.ingsw.server.model.exceptions.GameStartException;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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

    public PlayerController getPlayerController(Player player) {
        for (PlayerController controller : playerControllers) {
            if (controller.getPlayer().equals(player)) {
                return controller;
            }
        }
        return null;
    }

    public PlayerController getPlayerController(String nickname) {
        for (PlayerController controller : playerControllers) {
            if (controller.getPlayer().getNickname().equals(nickname)) {
                return controller;
            }
        }
        return null;
    }

    public void addPlayer(String nickname) throws DuplicatePlayerException, FullLobbyException {
        if (getPlayerByNick(nickname).isPresent()) {
            throw new DuplicatePlayerException(nickname);
        }
        if (game.getPlayers().size() >= 4) {
            throw new FullLobbyException();
        }

        Player player = new Player(nickname, game);
        PlayerController controller = new PlayerController(player);
        playerControllers.add(controller);
        game.getPlayers().add(player);
        game.getInfo().setPlayersNumber(game.getPlayers().size());
    }

    public void removePlayer(Player player) {
        game.getPlayers().remove(player);
        game.getInfo().setPlayersNumber(game.getPlayers().size());
    }

    public synchronized void createGame(int gameId){
        game = new Game(gameId);
        game.getInfo().setGameStatus(GameStatusEnum.WAITING_FOR_PLAYERS);
    }

    public synchronized void startGame() throws GameStartException {
        try {

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
        } catch (Exception e) {
            game.getInfo().setGameStatus(GameStatusEnum.ERROR);
            System.err.println(e.getMessage());
            throw new GameStartException();
        }
    }

    public void assignStarterCard() {
        int size = game.getTable().getStarterCards().size();
        for (Player player : game.getPlayers()) {
            StarterCard starterCard = game.getTable().getStarterCards().remove(new Random().nextInt(size));
            player.setStarterCard(starterCard);
            size--;
        }
    }

    public void assignCommonObjectives() {
        int size = game.getTable().getObjectiveCards().size();
        game.addObjectiveCard(game.getTable().getObjectiveCards().remove(new Random().nextInt(size)));
        game.addObjectiveCard(game.getTable().getObjectiveCards().remove(new Random().nextInt(size - 1)));
    }

    public void instantiateCards() {
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
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            StarterCard card = new StarterCard(file);
            game.getTable().addStarterCard(card);
        }

        folder = Paths.get("src/main/resources/assets/objectivecards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ObjectiveCard card = new ObjectiveCard(file);
            game.getTable().addObjectiveCard(card);
        }
    }

    public void nextTurn() {
        Player next = nextPlayer();
        game.getInfo().setActivePlayer(next);
    }

    public Player nextPlayer() {
        int index = game.getPlayers().indexOf(game.getInfo().getActivePlayer());
        return game.getPlayers().get((index + 1) % game.getPlayers().size());
    }

    public void chooseFirstPlayer() {
        Player first = game.getPlayers().get(new Random().nextInt(game.getPlayers().size()));
        first.setFirst(true);
        game.getInfo().setFirstPlayer(first);
        game.getInfo().setActivePlayer(first);
    }

    public void endGame() {
        for (Player p : game.getPlayers()) {
            int secretObjectivePoints = p.getObjectiveCard().getObjective().getPoints(p.getCards());
            int publicObjectivePoints = 0;
            for (ObjectiveCard card : game.getObjectiveCards()) {
                publicObjectivePoints += card.getObjective().getPoints(p.getCards());
            }
            p.setScore(secretObjectivePoints + publicObjectivePoints);
        }
    }

    public Optional<Player> getPlayerByNick(String nick) {
        for (Player p : game.getPlayers()) {
            if (p.getNickname().equalsIgnoreCase(nick)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
