package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.*;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.exceptions.*;
import it.polimi.ingsw.server.controller.save.CardSave;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.controller.save.PlayerSave;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.player.TokenColorEnum;

import java.io.*;
import java.util.*;

public class GameController {

    private Game game;
    private final ArrayList<Card> allCards;
    private final ArrayList<PlayerController> playerControllers;
    private final ServerNetworkHandler networkHandler;

    public GameController(ServerNetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
        this.playerControllers = new ArrayList<>();
        this.allCards = new ArrayList<>(80);
    }

    public Game getGame() {
        return game;
    }

    public ServerNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public synchronized List<PlayerController> getPlayerControllers() {
        return playerControllers;
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

    public synchronized void addPlayer(String username) throws DuplicatePlayerException, FullLobbyException {
        if (hasDisconnected(username)) {
            reconnectPlayer(username);
            return;
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

    }

    public void reconnectPlayer(String player) {
        Iterator<Player> iterator = game.getOfflinePlayers().iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getUsername().equalsIgnoreCase(player)) {
                game.getPlayers().add(p);
                iterator.remove();
                return;
            }
        }
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

        Player playerToRemove = null;
        for (Player currPlayer : game.getPlayers()) {
            if (currPlayer.getUsername().equals(player)) {
                playerToRemove = currPlayer;
            }
        }
        if (playerToRemove != null) {
            playerControllers.remove(getPlayerController(playerToRemove));
            game.getPlayers().remove(playerToRemove);

        } else {
            throw new PlayerNotFoundException(player);
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
            networkHandler.sendPacketToAll(new InfoPacket(Printer.GREEN + "\nThe required number of players has been reached. The game is starting.\n" + Printer.RESET));
            game.getInfo().setGameStatus(GameStatusEnum.STARTING);

            instantiateCards();
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

            game.getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        } catch (Exception e) {
            game.getInfo().setGameStatus(GameStatusEnum.ERROR);
            System.err.println(e.getMessage());
            throw new GameStartException();
        }
    }

    public synchronized boolean checkExistingSaves() {
        File saveFolder = new File(System.getenv("APPDATA") + "/CodexNaturalisSaves/");
        return saveFolder.listFiles() != null && Objects.requireNonNull(saveFolder.listFiles()).length > 0;
    }

    public synchronized void loadGameFromFile() {
        File saveDir = new File(System.getenv("APPDATA") + "/CodexNaturalisSaves/");
        File saveFile = Objects.requireNonNull(saveDir.listFiles())[0];
        GameSave save = null;
        try (FileInputStream fileIn = new FileInputStream(saveFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            save = (GameSave) in.readObject();
        } catch (Exception e) {
            System.err.println("Error while loading the game: " + e.getMessage());
        }
        if (save == null) return;

        createGame(save.getId());
        game.getInfo().setMaxPlayers(save.getMaxPlayers());
        game.getInfo().setGameStatus(GameStatusEnum.valueOf(save.getGameStatus()));

        Deck objectiveDeck = new Deck();
        for(CardSave cardSave : save.getObjectiveDeck()) {
            objectiveDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setObjectiveDeck(objectiveDeck);
        Deck resourceDeck = new Deck();
        for(CardSave cardSave : save.getResourceDeck()) {
            resourceDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setResourceDeck(resourceDeck);
        Deck goldDeck = new Deck();
        for(CardSave cardSave : save.getGoldDeck()) {
            goldDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setGoldDeck(goldDeck);
        Deck starterDeck = new Deck();
        for(CardSave cardSave : save.getStarterDeck()) {
            starterDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setStarterDeck(starterDeck);
        for (CardSave cardSave : save.getCardsOnGround()) {
            game.getTable().addCardOnGround(getCardById(cardSave.getId()));
        }
        for (CardSave cardSave : save.getObjectiveCards()) {
            game.getTable().addObjectiveCard((ObjectiveCard) getCardById(cardSave.getId()));
        }

        for (PlayerSave playerSave : save.getPlayerSaves()) {
            Player player = new Player(playerSave.getUsername(), game);
            player.setScore(playerSave.getScore());
            player.setToken(new PlayerToken(TokenColorEnum.valueOf(playerSave.getToken())));
            player.setObjectiveCard((ObjectiveCard) getCardById(playerSave.getObjectiveCard().getId()));
            player.setStarterCard((StarterCard) getCardById(playerSave.getStarterCard().getId()));
            for (CardSave cardSave : playerSave.getCardsInHand()) {
                Card card = getCardById(cardSave.getId());
                card.setFace(FaceEnum.valueOf(cardSave.getFace()));
                player.addCardInHand(card);

            }
            for (int i = 0; i < 81; i++) {
                for (int j = 0; j < 81; j++) {
                    CardSave cardSave = playerSave.getCards()[i][j];
                    if (cardSave != null) {
                        Card card = getCardById(playerSave.getCards()[i][j].getId());
                        card.setFace(FaceEnum.valueOf(cardSave.getFace()));
                        player.setCard((ResourceCard) card, i, j);
                    }
                }
            }
            game.getPlayers().add(player);
        }
        //TODO: Player resources
    }

    public synchronized void saveGameToFile() {
        try {
            GameSave save = new GameSave(game);
            File saveDir = new File(System.getenv("APPDATA") + "\\CodexNaturalisSaves");
            saveDir.mkdirs();
            File saveFile = new File(System.getenv("APPDATA") + "\\CodexNaturalisSaves\\game" + game.getInfo().getId() + ".save");
            saveFile.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(save);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Error while saving the game: " + e.getMessage());
        }
    }

    public synchronized void proposeStarterCardFace() {
        for (Player player : game.getPlayers()) {
            StarterCard starterCard = (StarterCard) game.getTable().getStarterDeck().drawCard();
            networkHandler.sendPacket(networkHandler.getConnectionByNickname(player.getUsername()), new ChooseStarterFacePacket(starterCard.getId(), player.getUsername()));
        }
    }

    public synchronized void assignCommonObjectives() {
        game.getTable().addObjectiveCard((ObjectiveCard) game.getTable().getObjectiveDeck().drawCard());
        game.getTable().addObjectiveCard((ObjectiveCard) game.getTable().getObjectiveDeck().drawCard());
    }

    public synchronized void proposeObjectiveCards() {
        for (Player p : game.getPlayers()) {
            ObjectiveCard card1 = (ObjectiveCard) game.getTable().getObjectiveDeck().drawCard();
            ObjectiveCard card2 = (ObjectiveCard) game.getTable().getObjectiveDeck().drawCard();
            networkHandler.sendPacket(networkHandler.getConnectionByNickname(p.getUsername()), new ChooseObjectivePacket(card1.getId(), card2.getId()));
        }
    }

    public synchronized void instantiateCards() throws IOException {
        BufferedReader reader;
        Deck resourceDeck = new Deck();
        for (int i = 1; i <= 40; i++) {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ResourceCard card = new ResourceCard(jsonData);
            resourceDeck.addCard(card);
            allCards.add(card);
        }
        resourceDeck.shuffleDeck();
        game.getTable().setResourceDeck(resourceDeck);

        Deck goldDeck = new Deck();
        for (int i = 1; i <= 40; i++) {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/goldcards/goldCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            GoldCard card = new GoldCard(jsonData);
            goldDeck.addCard(card);
            allCards.add(card);
        }
        goldDeck.shuffleDeck();
        game.getTable().setGoldDeck(goldDeck);

        Deck starterDeck = new Deck();
        for (int i = 1; i <= 6; i++) {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/startercards/starterCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            StarterCard card = new StarterCard(jsonData);
            starterDeck.addCard(card);
            allCards.add(card);
        }
        starterDeck.shuffleDeck();
        game.getTable().setStarterDeck(starterDeck);

        Deck objectiveDeck = new Deck();
        for (int i = 1; i <= 16; i++) {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveDeck.addCard(card);
            allCards.add(card);
        }
        objectiveDeck.shuffleDeck();
        game.getTable().setObjectiveDeck(objectiveDeck);
    }

    public synchronized void nextTurn() {
        saveGameToFile();

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
        networkHandler.sendPacket(networkHandler.getConnectionByNickname(first.getUsername()), new InfoPacket(Printer.CYAN + "You have been selected as the first Player" + Printer.RESET));
    }

    public synchronized void checkEndCondition() {

        if (game.getPlayers().stream().anyMatch(player -> player.getScore() >= 20) || (game.getTable().getCardsOnGround().isEmpty() && game.getTable().getResourceDeck().getCards().isEmpty() && game.getTable().getGoldDeck().getCards().isEmpty())) {
            int firstPlayerIndex = game.getPlayers().indexOf(game.getInfo().getFirstPlayer());
            Player lastPlayer = game.getPlayers().get(firstPlayerIndex != 0 ? firstPlayerIndex - 1 : game.getInfo().getPlayersNumber() - 1);

            if (game.getInfo().getActivePlayer().equals(lastPlayer)) {
                if (game.getInfo().getGameStatus().equals(GameStatusEnum.LAST_TURN)) {
                    game.getInfo().setGameStatus(GameStatusEnum.ENDING);
                    networkHandler.sendPacketToAll(new InfoPacket(Printer.CYAN + "Last turns ended, calculating the winner(s)..." + Printer.RESET));
                    endGame();
                } else {
                    game.getInfo().setGameStatus(GameStatusEnum.LAST_TURN);
                    networkHandler.sendPacketToAll(new InfoPacket(Printer.CYAN + "Game is ending, play your last turn!" + Printer.RESET));
                }
            }
        }
    }

    public synchronized void endGame() {

        HashMap<Player, Integer> objectiveCardsScored = new HashMap<>();
        HashMap<String, Integer> playerScores = new HashMap<>();

        for (Player p : game.getPlayers()) {
            int currentObjectiveCardsScored = 0;

            int secretObjectivePoints = p.getObjectiveCard().calculatePoints(p);
            currentObjectiveCardsScored += secretObjectivePoints == 0 ? 0 : 1;

            int publicObjectivePoints = 0;
            for (ObjectiveCard card : game.getTable().getObjectiveCards()) {
                publicObjectivePoints += card.calculatePoints(p);
                currentObjectiveCardsScored += card.calculatePoints(p) == 0 ? 0 : 1;
            }

            p.setScore(p.getScore() + secretObjectivePoints + publicObjectivePoints);
            playerScores.put(p.getUsername(), p.getScore());
            objectiveCardsScored.put(p, currentObjectiveCardsScored);
        }

        //Calculate the player or the players with the maximum score
        Optional<Integer> winningPoints = game.getPlayers().stream().map(Player::getScore).max((Integer::compare));
        ArrayList<Player> winners = new ArrayList<>(4);

        for (Player p : game.getPlayers()) {
            if (winningPoints.isPresent() && winningPoints.get().equals(p.getScore()))
                winners.add(p);
        }

        //If winners are more than one, query the ObjectiveCardsScored map
        if (winners.size() == 1) {
            game.getInfo().addWinner(winners.getFirst());
            networkHandler.sendPacketToAll(new GameEndedPacket(winners.stream().map(Player::getUsername).toList(), playerScores));
        } else {
            Optional<Integer> maxObjectivesScored = objectiveCardsScored.values().stream().max(Integer::compare);

            winners.removeIf(p -> maxObjectivesScored.isPresent() && !objectiveCardsScored.get(p).equals(maxObjectivesScored.get()));
            if (winners.size() == 1) {
                game.getInfo().addWinner(winners.getFirst());
                networkHandler.sendPacketToAll(new GameEndedPacket(winners.stream().map(Player::getUsername).toList(), playerScores));
            } else {
                winners.forEach(player -> game.getInfo().addWinner(player));
                networkHandler.sendPacketToAll(new GameEndedPacket(winners.stream().map(Player::getUsername).toList(), playerScores));
            }
        }
        getNetworkHandler().stop();
        ServerMain.removeMatch(getNetworkHandler());
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

    public synchronized Card getCardById(int ID) {
        for (Card card : allCards) {
            if (card.getId() == ID) {
                return card;
            }
        }
        return null;
    }
}
