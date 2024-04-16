package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.model.exceptions.FullLobbyException;
import it.polimi.ingsw.server.model.exceptions.GameStartException;
import it.polimi.ingsw.server.model.player.Player;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class Game {

    private final int id;
    private final Table table;
    private final List<Player> players;
    private final ArrayList<ObjectiveCard> objectiveCards;
    private GameInfo info;
    private GameController controller;

    public Game(int id) {
        this.id = id;
        this.table = new Table();
        this.players = new ArrayList<>();
        this.objectiveCards = new ArrayList<>();
        this.info = new GameInfo(0);
        this.info.setGameStatus(GameStatusEnum.WAITING_FOR_PLAYERS);
    }

    public int getId() {
        return id;
    }

    public Table getTable() {
        return table;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) throws DuplicatePlayerException, FullLobbyException {
        if (getPlayerByNick(player.getNickname()).isPresent()) {
            throw new DuplicatePlayerException(player.getNickname());
        }
        if (players.size() >= 4) {
            throw new FullLobbyException();
        }
        this.players.add(player);
        this.getInfo().setPlayersNumber(players.size());
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.getInfo().setPlayersNumber(players.size());
    }

    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return objectiveCards;
    }

    public void addObjectiveCard(ObjectiveCard objectiveCard) {
        this.objectiveCards.add(objectiveCard);
    }

    public GameInfo getInfo() {
        return info;
    }

    public void setInfo(GameInfo info) {
        this.info = info;
    }

    public void startGame() throws GameStartException {
        try {
            getInfo().setGameStatus(GameStatusEnum.STARTING);

            instantiateCards();
            assignStarterCard();
            assignCommonObjectives();
            chooseFirstPlayer();

            getTable().addCardOnGround(getTable().getResourceDeck().drawCard());
            getTable().addCardOnGround(getTable().getResourceDeck().drawCard());
            getTable().addCardOnGround(getTable().getGoldDeck().drawCard());
            getTable().addCardOnGround(getTable().getGoldDeck().drawCard());

            for (Player p : players) {
                p.addCardInHand(getTable().getResourceDeck().drawCard());
                p.addCardInHand(getTable().getResourceDeck().drawCard());
                p.addCardInHand(getTable().getGoldDeck().drawCard());
            }
        } catch (Exception e) {
            getInfo().setGameStatus(GameStatusEnum.ERROR);
            throw new GameStartException();
        }
    }

    public void assignStarterCard() {
        int size = getTable().getStarterCards().size();
        for (Player player : players) {
            StarterCard starterCard = getTable().getStarterCards().remove(new Random().nextInt(size));
            player.setStarterCard(starterCard);
            size--;
        }
    }

    public void assignCommonObjectives() {
        int size = getTable().getObjectiveCards().size();
        addObjectiveCard(getTable().getObjectiveCards().remove(new Random().nextInt(size)));
        addObjectiveCard(getTable().getObjectiveCards().remove(new Random().nextInt(size - 1)));
    }

    public void instantiateCards() {
        File folder = Paths.get("src/main/resources/assets/resourcecards").toFile();
        Deck resourceDeck = new Deck();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ResourceCard card = new ResourceCard(file);
            resourceDeck.addCard(card);
        }
        resourceDeck.shuffleDeck();
        getTable().setResouceDeck(resourceDeck);


        folder = Paths.get("src/main/resources/assets/goldcards").toFile();
        Deck goldDeck = new Deck();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            GoldCard card = new GoldCard(file);
            goldDeck.addCard(card);
        }
        goldDeck.shuffleDeck();
        getTable().setGoldDeck(goldDeck);

        folder = Paths.get("src/main/resources/assets/startercards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            StarterCard card = new StarterCard(file);
            getTable().addStarterCard(card);
        }

        folder = Paths.get("src/main/resources/assets/objectivecards").toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            ObjectiveCard card = new ObjectiveCard(file);
            getTable().addObjectiveCard(card);
        }
    }

    public void nextTurn() {
        Player next = nextPlayer();
        getInfo().setActivePlayer(next);
    }

    public Player nextPlayer() {
        int index = players.indexOf(getInfo().getActivePlayer());
        return players.get((index + 1) % players.size());
    }

    public void chooseFirstPlayer() {
        Player first = players.get(new Random().nextInt(players.size()));
        first.setFirst(true);
        getInfo().setFirstPlayer(first);
        getInfo().setActivePlayer(first);
    }

    public void endGame() {
        for (Player p : players) {
            int secretObjectivePoints = p.getObjectiveCard().getObjective().getPoints(p.getCards());
            int publicObjectivePoints = 0;
            for (ObjectiveCard card : objectiveCards) {
                publicObjectivePoints += card.getObjective().getPoints(p.getCards());
            }
            p.setScore(secretObjectivePoints + publicObjectivePoints);
        }
    }

    public Optional<Player> getPlayerByNick(String nick) {
        for (Player p : players) {
            if (p.getNickname().equalsIgnoreCase(nick)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
