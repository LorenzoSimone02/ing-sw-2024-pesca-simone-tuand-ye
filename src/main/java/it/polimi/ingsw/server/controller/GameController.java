package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.*;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.controller.exceptions.FullLobbyException;
import it.polimi.ingsw.server.controller.exceptions.GameStartException;
import it.polimi.ingsw.server.controller.exceptions.IllegalOperationForStateException;
import it.polimi.ingsw.server.controller.save.CardSave;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.controller.save.PlayerSave;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;
import it.polimi.ingsw.server.model.resources.Resource;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.io.*;
import java.util.*;

/**
 * The class that represents the game controller
 */
public class GameController {

    /**
     * The current game
     */
    private Game game;

    /**
     * The ID of the saved game
     */
    private final int saveGameId;

    /**
     * The file in which the game is saved
     */
    private File saveGameFile;

    /**
     * The list of all the cards
     */
    private final ArrayList<Card> allCards;

    /**
     * The list of the player controllers
     */
    private final ArrayList<PlayerController> playerControllers;

    /**
     * The network handler of the game
     */
    private final ServerNetworkHandler networkHandler;

    /**
     * The constructor of the class
     * @param networkHandler the network handler of the game
     */
    public GameController(ServerNetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
        this.playerControllers = new ArrayList<>();
        this.allCards = new ArrayList<>(80);
        this.saveGameId = new Random().nextInt(99999);
    }

    /**
     * The method returns the current game
     * @return the current game
     */
    public Game getGame() {
        return game;
    }

    /**
     * The method returns the network handler of the game
     * @return the network handler of the game
     */
    public ServerNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    /**
     * The method returns the list of the player controllers
     * @return the list of the player controllers
     */
    public synchronized List<PlayerController> getPlayerControllers() {
        return playerControllers;
    }

    /**
     * The method returns the player controller of the player
     * @param player the player whose controller is being returned
     * @return the controller of the player
     */
    public synchronized PlayerController getPlayerController(Player player) {
        for (PlayerController controller : playerControllers) {
            if (controller.getPlayer().equals(player)) {
                return controller;
            }
        }
        return null;
    }

    /**
     * The method returns the player controller of the player given the nickname
     * @param nickname the nickname of the player whose controller is being returned
     * @return the controller of the player
     */
    public synchronized PlayerController getPlayerController(String nickname) {
        for (PlayerController controller : playerControllers) {
            if (controller.getPlayer().getUsername().equals(nickname)) {
                return controller;
            }
        }
        return null;
    }

    /**
     * The method sets the maximum number of players
     * @param playersNumber the maximum number of players
     */
    public synchronized void setMaxPlayers(int playersNumber) {
        game.getInfo().setMaxPlayers(playersNumber);
    }

    /**
     * The method adds a player to the game
     * @param username the username of the player to add
     */
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

    /**
     * The method reconnects a player to the game
     * @param player the player to reconnect
     */
    public void reconnectPlayer(String player) {
        Iterator<Player> iterator = game.getOfflinePlayers().iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getUsername().equalsIgnoreCase(player)) {
                game.getPlayers().add(p);
                PlayerController controller = new PlayerController(p);
                playerControllers.add(controller);
                iterator.remove();
                networkHandler.sendPacketToAll(new ConnectionEventPacket(player, false, true));
                if (game.getPlayers().size() > 1)
                    game.getInfo().setGameStatus(GameStatusEnum.PLAYING);
                return;
            }
        }
    }

    /**
     * The method defines the controller's behavior whenever a player disconnects
     * @param username the username of the player who disconnected
     */
    public synchronized void onDisconnect(String username) {
        Optional<Player> player = getPlayerByNick(username);
        if (player.isPresent()) {
            if (game.getInfo().getGameStatus().equals(GameStatusEnum.PLAYING)) {
                game.getOfflinePlayers().add(player.get());
            }
            removePlayer(player.get());
            networkHandler.sendPacketToAll(new ConnectionEventPacket(username, true, false));
            if (game.getInfo().getGameStatus().equals(GameStatusEnum.PLAYING) && game.getInfo().getActivePlayer().getUsername().equals(username)) {
                nextTurn();
            }
            checkPreGameConditions();
        }
    }

    /**
     * The method removes a player from the game
     * @param player the player to remove
     */
    public synchronized void removePlayer(Player player) {
        if (game == null) return;

        playerControllers.remove(getPlayerController(player));
        game.getPlayers().remove(player);

        if (game.getInfo().getPlayersNumber() == 1) {
            if (game.getInfo().getGameStatus().equals(GameStatusEnum.PLAYING)) {
                game.getInfo().setGameStatus(GameStatusEnum.WAITING_FOR_PLAYERS);
                networkHandler.sendPacketToAll(new InfoPacket(Printer.RED + "There is currently only one player connected, wait for someone to reconnect." + Printer.RESET));
            } else {
                networkHandler.sendPacketToAll(new InfoPacket(Printer.RED + "There aren't enough player to proceed, game forcefully terminated." + Printer.RESET));
                endGame();
            }
        }
    }

    /**
     * The method checks if the player has disconnected
     * @param player the player to check
     * @return true if the player has disconnected
     */
    public boolean hasDisconnected(String player) {
        if (game == null) return false;
        for (Player players : game.getOfflinePlayers()) {
            if (players.getUsername().equalsIgnoreCase(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method creates a new game
     * @param gameId the ID assigned to the game
     */
    public synchronized void createGame(int gameId) {
        game = new Game(gameId);
    }

    /**
     * The method checks if all the conditions needed in order to start a previously saved game are met
     */
    public synchronized void checkStartCondition() {
        if (game.getPlayers().size() == game.getInfo().getMaxPlayers()) {
            GameSave save = checkExistingSave();
            if (save != null) {
                System.out.println("Previous save found, loading...");
                loadGameFromSave(save);
                return;
            }
            startGame();
        }
    }

    /**
     * The method checks the last game status of the previously saved game
     */
    public synchronized void checkPreGameConditions() {
        if (game.getInfo().getGameStatus().equals(GameStatusEnum.CHOOSING_COLOR)) {
            for (PlayerController playerController : getPlayerControllers()) {
                if (playerController.getPlayer().getToken() == null) {
                    return;
                }
            }
            networkHandler.sendPacketToAll(new InfoPacket(Printer.GREEN + "All players have chosen their Token Color." + Printer.RESET));
            game.getInfo().setGameStatus(GameStatusEnum.CHOOSING_STARTER_FACE);
            proposeStarterCardFace();
        }
        if (game.getInfo().getGameStatus().equals(GameStatusEnum.CHOOSING_STARTER_FACE)) {
            for (PlayerController playerController : getPlayerControllers()) {
                if (playerController.getPlayer().getStarterCard() == null) {
                    return;
                }
            }
            networkHandler.sendPacketToAll(new InfoPacket(Printer.GREEN + "All players have chosen their Starter Card face." + Printer.RESET));
            game.getInfo().setGameStatus(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);
            proposeObjectiveCards();
        }
        if (game.getInfo().getGameStatus().equals(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE)) {
            for (PlayerController playerController : getPlayerControllers()) {
                if (playerController.getPlayer().getObjectiveCard() == null) {
                    return;
                }
            }
            game.getInfo().setGameStatus(GameStatusEnum.PLAYING);
            networkHandler.sendPacketToAll(new InfoPacket(Printer.GREEN + "All players have chosen their Objective Cards, the first turn is starting." + Printer.RESET));
            networkHandler.sendPacketToAll(new EndTurnPacket(game.getInfo().getFirstPlayer().getUsername()));
            saveGameToFile();
        }
    }

    /**
     * The method starts the game
     * @throws GameStartException if an error occurs while starting the game
     */
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

            GameStartedPacket gameStartedPacket = new GameStartedPacket(game, false);
            networkHandler.sendPacketToAll(gameStartedPacket);

            game.getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        } catch (Exception e) {
            game.getInfo().setGameStatus(GameStatusEnum.ERROR);
            System.err.println(e.getMessage());
            throw new GameStartException();
        }
    }

    /**
     * The method sets the default directory for the saved games
     * @return the default directory for the saved games
     */
    private String setDefaultDirectory() {
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN"))
            return System.getenv("APPDATA") + "/CodexNaturalisSaves/";
        else if (OS.contains("MAC")){
            new File(System.getProperty("user.home") + "/Library/Application Support/CodexNaturalisSaves/").mkdirs();
            return System.getProperty("user.home") + "/Library/Application Support/CodexNaturalisSaves/";
        }
        else if (OS.contains("NUX")){
            new File(System.getProperty("user.home") + "/.codexNaturalisSaves/").mkdirs();
            return System.getProperty("user.home") + "/.codexNaturalisSaves/";
        }
        return System.getProperty("user.dir");
    }

    /**
     * The method checks if a previous saved game exists in the default directory
     */
    public synchronized GameSave checkExistingSave() {
        File saveFolder = new File(setDefaultDirectory());
        for (File file : Objects.requireNonNull(saveFolder.listFiles())) {
            GameSave save;
            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                save = (GameSave) in.readObject();
                if (save.getPlayerSaves().size() == game.getInfo().getPlayersNumber()) {
                    for (PlayerSave playerSave : save.getPlayerSaves()) {
                        if (getPlayerByNick(playerSave.getUsername()).isEmpty()) {
                            return null;
                        }
                    }
                    in.close();
                    fileIn.close();
                    file.delete();
                    return save;
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * The method loads a previously saved game
     * @param gameSave the saved game to load
     */
    public synchronized void loadGameFromSave(GameSave gameSave) {
        try {
            instantiateCards();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game.getInfo().setMaxPlayers(gameSave.getMaxPlayers());
        game.getInfo().setGameStatus(GameStatusEnum.valueOf(gameSave.getGameStatus()));

        Deck objectiveDeck = new Deck();
        for (CardSave cardSave : gameSave.getObjectiveDeck()) {
            objectiveDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setObjectiveDeck(objectiveDeck);
        Deck resourceDeck = new Deck();
        for (CardSave cardSave : gameSave.getResourceDeck()) {
            resourceDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setResourceDeck(resourceDeck);
        Deck goldDeck = new Deck();
        for (CardSave cardSave : gameSave.getGoldDeck()) {
            goldDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setGoldDeck(goldDeck);
        Deck starterDeck = new Deck();
        for (CardSave cardSave : gameSave.getStarterDeck()) {
            starterDeck.addCard(getCardById(cardSave.getId()));
        }
        game.getTable().setStarterDeck(starterDeck);
        for (CardSave cardSave : gameSave.getCardsOnGround()) {
            game.getTable().addCardOnGround(getCardById(cardSave.getId()));
        }
        for (CardSave cardSave : gameSave.getObjectiveCards()) {
            game.getTable().addObjectiveCard((ObjectiveCard) getCardById(cardSave.getId()));
        }

        for (PlayerSave playerSave : gameSave.getPlayerSaves()) {
            Player player = getPlayerByNick(playerSave.getUsername()).orElseThrow();
            player.setScore(playerSave.getScore());
            player.setToken(new PlayerToken(TokenColorEnum.valueOf(playerSave.getTokenColor())));
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
            for (String resOrObj : playerSave.getResourcesAndObjects().keySet()) {
                for (int i = 0; i < playerSave.getResourcesAndObjects().getOrDefault(resOrObj, 0); i++) {
                    try {
                        player.addResource(new Resource(ResourceTypeEnum.valueOf(resOrObj)));
                    } catch (IllegalArgumentException e) {
                        player.addObject(new Object(ObjectTypeEnum.valueOf(resOrObj)));
                    }
                }
            }
            if (player.getUsername().equals(gameSave.getActivePlayer())) {
                game.getInfo().setActivePlayer(player);
            }
            if (player.getUsername().equals(gameSave.getFirstPlayer())) {
                game.getInfo().setFirstPlayer(player);
            }
        }
        for (Player player : game.getPlayers()) {
            ClientConnection connection = networkHandler.getConnectionByNickname(player.getUsername());
            networkHandler.sendPacket(connection, new GameStartedPacket(game, true));
            networkHandler.sendPacket(connection, new RestoreGameStatePacket(player.getUsername(), gameSave.getPlayerSaves()));
            networkHandler.sendPacket(connection, new InfoPacket(Printer.GREEN + "You previous Game has been restored." + Printer.RESET));
            networkHandler.sendPacket(connection, new EndTurnPacket(game.getInfo().getActivePlayer().getUsername()));
        }
    }

    /**
     * The method saves the game data to a file in the default directory
     */
    public synchronized void saveGameToFile() {
        try {
            GameSave save = new GameSave(game);
            File saveDir = new File(setDefaultDirectory());
            saveDir.mkdirs();
            File saveFile = new File(setDefaultDirectory() + "/game" + saveGameId + ".save");
            if (saveFile.createNewFile())
                saveGameFile = saveFile;
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(save);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Error while saving the game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The method proposes the starter card faces to the players
     */
    public synchronized void proposeStarterCardFace() {
        for (Player player : game.getPlayers()) {
            StarterCard starterCard = (StarterCard) game.getTable().getStarterDeck().drawCard();
            networkHandler.sendPacket(networkHandler.getConnectionByNickname(player.getUsername()), new ChooseStarterFacePacket(starterCard.getId(), player.getUsername()));
        }
    }

    /**
     * The method sets the common objectives of the game
     */
    public synchronized void assignCommonObjectives() {
        game.getTable().addObjectiveCard((ObjectiveCard) game.getTable().getObjectiveDeck().drawCard());
        game.getTable().addObjectiveCard((ObjectiveCard) game.getTable().getObjectiveDeck().drawCard());
    }

    /**
     * The method proposes the objective cards to the players
     */
    public synchronized void proposeObjectiveCards() {
        for (Player p : game.getPlayers()) {
            ObjectiveCard card1 = (ObjectiveCard) game.getTable().getObjectiveDeck().drawCard();
            ObjectiveCard card2 = (ObjectiveCard) game.getTable().getObjectiveDeck().drawCard();
            networkHandler.sendPacket(networkHandler.getConnectionByNickname(p.getUsername()), new ChooseObjectivePacket(card1.getId(), card2.getId()));
        }
    }

    /**
     * The method instantiates all the cards of the game
     * @throws IOException if an error occurs while instantiating the cards
     */
    public synchronized void instantiateCards() throws IOException {
        BufferedReader reader = null;
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
        reader.close();
        objectiveDeck.shuffleDeck();
        game.getTable().setObjectiveDeck(objectiveDeck);
    }

    /**
     * The method sets the current turn to the next player
     */
    public synchronized void nextTurn() {
        checkEndCondition();

        Player next = nextPlayer();
        game.getInfo().setActivePlayer(next);
        networkHandler.sendPacketToAll(new EndTurnPacket(next.getUsername()));
        saveGameToFile();
    }

    /**
     * The method returns the next player
     * @return the next player
     */
    public synchronized Player nextPlayer() {
        int index = game.getPlayers().indexOf(game.getInfo().getActivePlayer());
        return game.getPlayers().get((index + 1) % game.getPlayers().size());
    }

    /**
     * The method randomly chooses the first player
     */
    public synchronized void chooseFirstPlayer() {
        Player first = game.getPlayers().get(new Random().nextInt(game.getPlayers().size()));
        game.getInfo().setFirstPlayer(first);
        game.getInfo().setActivePlayer(first);
        networkHandler.sendPacket(networkHandler.getConnectionByNickname(first.getUsername()), new InfoPacket(Printer.CYAN + "You have been selected as the first Player" + Printer.RESET));
    }

    /**
     * The method checks if the conditions to end the current game are met
     */
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

    /**
     * The method ends the current game by calculating all the players' scores and declare the winner
     */
    public synchronized void endGame() {

        if (!game.getInfo().getGameStatus().equals(GameStatusEnum.ENDING)) {
            game.getInfo().setGameStatus(GameStatusEnum.ENDING);
            ServerMain.removeMatch(networkHandler);
            ServerNetworkHandler lobby = ServerMain.getLobby();
            for (Player p : game.getPlayers()) {
                ClientConnection connection = networkHandler.getConnectionByNickname(p.getUsername());
                lobby.addConnection(connection);
                lobby.sendPacket(connection, new InfoPacket("You have been connected to the Lobby"));
                lobby.sendPacket(connection, new JoinPacket(-1));
            }
            networkHandler.stop();
            return;
        }

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
        System.out.println(saveGameFile.getAbsolutePath());
        System.out.println(saveGameFile.delete());
        networkHandler.stop();
        ServerMain.removeMatch(networkHandler);
    }

    /**
     * The method returns the player with the given nickname
     * @return the player with the given nickname
     */
    public synchronized Optional<Player> getPlayerByNick(String nick) {
        if (game == null) return Optional.empty();
        for (Player p : game.getPlayers()) {
            if (p.getUsername().equalsIgnoreCase(nick)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * The method returns the card with the given ID
     * @return the card with the given ID
     */
    public synchronized Card getCardById(int ID) {
        for (Card card : allCards) {
            if (card.getId() == ID) {
                return card;
            }
        }
        return null;
    }
}
