package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.client.view.gui.gestures.GroundCardGestures;
import it.polimi.ingsw.client.view.gui.gestures.HandCardGestures;
import it.polimi.ingsw.client.view.gui.gestures.TableGestures;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameGuiController implements SceneController, Initializable {

    @FXML
    private HBox gamePane;
    @FXML
    private TabPane tables;
    @FXML
    private Pane playerTable;
    @FXML
    private Tab playerTab, opponentTab, opponentTab2, opponentTab3;
    @FXML
    private Pane opponentTable, opponentTable2, opponentTable3;
    private ArrayList<Pane> tablesPanes;
    @FXML
    private HBox hand;
    @FXML
    private ImageView card1, card2, card3;
    @FXML
    private ImageView cardObj;
    @FXML
    private VBox col1, col2;
    @FXML
    private Label animal, fungi, plant, insect, manuscript, inkwell, quill, turn;
    @FXML
    private StackPane tokens;
    @FXML
    private TextField messageInput;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messagesBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamePane.setOpacity(0.0);
        Platform.runLater(() -> {
            new TableGestures(playerTable);
            new TableGestures(opponentTable);
            for (Node card : hand.getChildren()) {
                if (card.getId().equals("cardObj")) continue;
                new HandCardGestures(playerTable, card, tables);
            }

            initializeOpponents();
            initializeCards();
            updateResources();
            updatePoints();
            updateTurn();

            ClientManager.getInstance().getNetworkHandler().sendPacket(new PeekDeckPacket(true));
            ClientManager.getInstance().getNetworkHandler().sendPacket(new PeekDeckPacket(false));

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(750), gamePane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
    }

    @FXML
    private void sendChatMessage() {
        String message = messageInput.getText();
        String username = ClientManager.getInstance().getGameState().getUsername();
        if (!message.trim().isEmpty()) {
            messageInput.clear();
            ClientManager.getInstance().getNetworkHandler().sendPacket(new ChatPacket(username, null, message));
        }
    }

    public void addMessage(String sender, String recipient, String message) {
        TextFlow messageFlow;
        if (recipient != null) {
            messageFlow = new TextFlow();
            Text senderText = new Text(sender + " -> " + recipient + ": ");
            senderText.setStyle("-fx-font-weight: bold;");
            Text messageText = new Text(message);
            messageFlow.getChildren().addAll(senderText, messageText);
        } else {
            messageFlow = new TextFlow();
            Text senderText = new Text(sender + ": ");
            senderText.setStyle("-fx-font-weight: bold;");
            Text messageText = new Text(message);
            messageFlow.getChildren().addAll(senderText, messageText);
        }
        messagesBox.getChildren().add(messageFlow);
        scrollPane.setVvalue(1);
    }

    private void updateResources() {
        for (String resources : ClientManager.getInstance().getGameState().getResources().keySet()) {
            switch (resources) {
                case "ANIMAL":
                    animal.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
                case "FUNGI":
                    fungi.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
                case "PLANT":
                    plant.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
                case "INSECT":
                    insect.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
                case "MANUSCRIPT":
                    manuscript.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
                case "INKWELL":
                    inkwell.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
                case "QUILL":
                    quill.setText("x" + ClientManager.getInstance().getGameState().getResources().get(resources));
                    break;
            }
        }
    }

    private void initializeCards() {
        try {
            int id = ClientManager.getInstance().getGameState().getCardsInHand().getFirst().getId();
            card1.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + id + ".png")).toURI().toString()));
            card1.setId(String.valueOf(id));
            int id2 = ClientManager.getInstance().getGameState().getCardsInHand().get(1).getId();
            card2.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + id2 + ".png")).toURI().toString()));
            card2.setId(String.valueOf(id2));
            int id3 = ClientManager.getInstance().getGameState().getCardsInHand().get(2).getId();
            card3.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + id3 + ".png")).toURI().toString()));
            card3.setId(String.valueOf(id3));

            ObjectiveCard objCard = ClientManager.getInstance().getGameState().getObjectiveCard();
            Tooltip tooltipObj = new Tooltip(objCard.getObjectiveDescription());
            Tooltip.install(cardObj, tooltipObj);
            cardObj.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + objCard.getId() + ".png")).toURI().toString()));

            ImageView resDeck = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/back1.png")).toURI().toString()));
            resDeck.setFitHeight(80);
            Rectangle resClip = new Rectangle(117, 80);
            resClip.setArcHeight(10);
            resClip.setArcWidth(10);
            resDeck.setClip(resClip);
            resDeck.setPreserveRatio(true);
            resDeck.setId("resDeck");
            new GroundCardGestures(resDeck);
            col1.getChildren().add(resDeck);

            ImageView goldDeck = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/back42.png")).toURI().toString()));
            goldDeck.setFitHeight(80);
            goldDeck.setPreserveRatio(true);
            Rectangle goldClip = new Rectangle(117, 80);
            goldClip.setArcHeight(10);
            goldClip.setArcWidth(10);
            goldDeck.setClip(goldClip);
            goldDeck.setId("goldDeck");
            new GroundCardGestures(goldDeck);
            col2.getChildren().add(goldDeck);

            for (int i = 0; i < ClientManager.getInstance().getGameState().getCardsOnGround().size(); i++) {
                Card card = ClientManager.getInstance().getGameState().getCardsOnGround().get(i);
                if (card instanceof ObjectiveCard) continue;
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + card.getId() + ".png")).toURI().toString()));
                imageView.setFitHeight(80);
                Rectangle clip = new Rectangle(117, 80);
                clip.setArcHeight(10);
                clip.setArcWidth(10);
                imageView.setClip(clip);
                imageView.setPreserveRatio(true);
                imageView.setId(String.valueOf(card.getId()));
                new GroundCardGestures(imageView);
                if (i % 2 == 0) {
                    col1.getChildren().add(imageView);
                } else {
                    col2.getChildren().add(imageView);
                }
            }

            boolean last = false;
            for (Card card : ClientManager.getInstance().getGameState().getCardsOnGround()) {
                if (!(card instanceof ObjectiveCard objectiveCard)) continue;
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + card.getId() + ".png")).toURI().toString()));
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(true);
                Rectangle clip = new Rectangle(117, 80);
                clip.setArcHeight(10);
                clip.setArcWidth(10);
                imageView.setClip(clip);
                Tooltip tooltip = new Tooltip(objectiveCard.getObjectiveDescription());
                tooltip.setShowDelay(Duration.millis(500));
                Tooltip.install(imageView, tooltip);
                if (!last) {
                    last = true;
                    imageView.setId("commObj1");
                    col1.getChildren().add(imageView);
                } else {
                    imageView.setId("commObj2");
                    col2.getChildren().add(imageView);
                }
            }
            setStarterCards();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeOpponents() {
        tablesPanes = new ArrayList<>();
        playerTable.setId(ClientManager.getInstance().getGameState().getUsername());
        playerTab.setText(ClientManager.getInstance().getGameState().getUsername());
        tablesPanes.add(playerTable);
        if (ClientManager.getInstance().getGameState().getPlayerStates().size() == 1) {
            String opp1 = ClientManager.getInstance().getGameState().getPlayerStates().getFirst().getUsername();
            opponentTable.setId(opp1);
            opponentTab.setText(opp1);
            tablesPanes.add(opponentTable);
            tables.getTabs().remove(opponentTab2);
            tables.getTabs().remove(opponentTab3);
        } else if (ClientManager.getInstance().getGameState().getPlayerStates().size() == 2) {
            String opp1 = ClientManager.getInstance().getGameState().getPlayerStates().getFirst().getUsername();
            String opp2 = ClientManager.getInstance().getGameState().getPlayerStates().get(1).getUsername();
            opponentTable.setId(opp1);
            opponentTab.setText(opp1);
            opponentTable2.setId(opp2);
            opponentTab2.setText(opp2);
            tables.getTabs().remove(opponentTab3);
            tablesPanes.add(opponentTable);
            tablesPanes.add(opponentTable2);
        } else if (ClientManager.getInstance().getGameState().getPlayerStates().size() == 3) {
            String opp1 = ClientManager.getInstance().getGameState().getPlayerStates().getFirst().getUsername();
            String opp2 = ClientManager.getInstance().getGameState().getPlayerStates().get(1).getUsername();
            String opp3 = ClientManager.getInstance().getGameState().getPlayerStates().get(2).getUsername();
            opponentTable.setId(opp1);
            opponentTab.setText(opp1);
            opponentTable2.setId(opp2);
            opponentTab2.setText(opp2);
            opponentTable3.setId(opp3);
            opponentTab3.setText(opp3);
            tablesPanes.add(opponentTable);
            tablesPanes.add(opponentTable2);
            tablesPanes.add(opponentTable3);
        }
    }

    public void addCardToHand(Card card) {
        try {
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + card.getId() + ".png")).toURI().toString()));
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setId(String.valueOf(card.getId()));
            Rectangle clip = new Rectangle(146, 100);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            imageView.setClip(clip);
            new HandCardGestures(playerTable, imageView, tables);
            hand.getChildren().add(imageView);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeCardFromGround(Card card) {
        for (Node node : col1.getChildren()) {
            if (node.getId().equals(String.valueOf(card.getId()))) {
                col1.getChildren().remove(node);
                return;
            }
        }
        for (Node node : col2.getChildren()) {
            if (node.getId().equals(String.valueOf(card.getId()))) {
                col2.getChildren().remove(node);
                return;
            }
        }
    }

    public void updateTopDeckCards() {
        try {
            for (Node node : col2.getChildren()) {
                if (node.getId().equals("goldDeck")) {
                    ImageView imageView = (ImageView) node;
                    imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/back" + ClientManager.getInstance().getGameState().getTopGoldDeckCard().getId() + ".png")).toURI().toString()));
                    break;
                }
            }
            for (Node node : col1.getChildren()) {
                if (node.getId().equals("resDeck")) {
                    ImageView imageView = (ImageView) node;
                    imageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/back" + ClientManager.getInstance().getGameState().getTopResourcesDeckCard().getId() + ".png")).toURI().toString()));
                    break;
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCardOnGround(Card card) {
        try {
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/front" + card.getId() + ".png")).toURI().toString()));
            imageView.setFitHeight(80);
            Rectangle clip = new Rectangle(117, 80);
            clip.setArcHeight(10);
            clip.setArcWidth(10);
            imageView.setClip(clip);
            imageView.setPreserveRatio(true);
            imageView.setId(String.valueOf(card.getId()));
            Node obj = null;
            if (col1.getChildren().size() == 3) {
                col1.getChildren().add(imageView);
                for (Node node : col1.getChildren()) {
                    if (node.getId().equals("commObj1")) {
                        obj = node;
                        col1.getChildren().remove(node);
                        break;
                    }
                }
                col1.getChildren().add(obj);

            } else if (col2.getChildren().size() == 3) {
                col2.getChildren().add(imageView);
                for (Node node : col2.getChildren()) {
                    if (node.getId().equals("commObj2")) {
                        obj = node;
                        col2.getChildren().remove(node);
                        break;
                    }
                }
                col2.getChildren().add(obj);
            }
            new GroundCardGestures(imageView);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeCard(ResourceCard card, int x, int y, String playerName) {
        Pane pane = null;
        for (Pane p : tablesPanes) {
            if (p.getId().equals(playerName)) {
                pane = p;
                break;
            }
        }
        if (pane == null) return;
        try {
            String face = card.getFace().name().toLowerCase();
            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/" + face + card.getId() + ".png")).toURI().toString()));
            imageView.setTranslateX((y * 55) - 73);
            imageView.setTranslateY((x * 29) - 50);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            imageView.setScaleX(0.5);
            imageView.setScaleY(0.5);
            Rectangle clip = new Rectangle(146, 100);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            imageView.setClip(clip);
            imageView.setId(String.valueOf(card.getId()));
            pane.getChildren().add(imageView);
            Bloom bloom = new Bloom();
            bloom.setThreshold(1);
            imageView.setEffect(bloom);

            var bloomTimeline = new Timeline(
                    new KeyFrame(Duration.millis(250), new KeyValue(bloom.thresholdProperty(), 1, Interpolator.EASE_IN)),
                    new KeyFrame(Duration.millis(750), new KeyValue(bloom.thresholdProperty(), 0, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(1250), new KeyValue(bloom.thresholdProperty(), 1, Interpolator.EASE_OUT))
            );

            bloomTimeline.play();
            hand.getChildren().removeIf(node -> node.getId().equals(String.valueOf(card.getId())));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStarterCards() {
        try {
            for (Pane pane : tablesPanes) {
                pane.setTranslateX(-1800);
                pane.setTranslateY(-855);
                String username = pane.getId();
                StackPane stackPane = new StackPane();
                StarterCard starterCard = null;
                if (pane.equals(playerTable)) {
                    starterCard = ClientManager.getInstance().getGameState().getStarterCard();
                } else {
                    for (PlayerState state : ClientManager.getInstance().getGameState().getPlayerStates()) {
                        if (state.getUsername().equals(username)) {
                            starterCard = state.getStarterCard();
                            break;
                        }
                    }
                }
                String face = starterCard.getFace().name().toLowerCase();
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/cards/" + face + starterCard.getId() + ".png")).toURI().toString()));
                imageView.setTranslateX((40 * 55) - 73);
                imageView.setTranslateY((40 * 29) - 50);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
                imageView.setPickOnBounds(true);
                imageView.setScaleX(0.5);
                imageView.setScaleY(0.5);
                Rectangle clip = new Rectangle(146, 100);
                clip.setArcWidth(10);
                clip.setArcHeight(10);
                imageView.setClip(clip);
                imageView.setId(String.valueOf(starterCard.getId()));
                stackPane.getChildren().add(imageView);

                String tokenName = null;
                if (pane.equals(playerTable)) {
                    tokenName = TokenColorEnum.valueOf(ClientManager.getInstance().getGameState().getTokenColor()).name().toLowerCase();
                } else {
                    for (PlayerState state : ClientManager.getInstance().getGameState().getPlayerStates()) {
                        if (state.getUsername().equals(username)) {
                            tokenName = state.getTokenColor().toLowerCase();
                            break;
                        }
                    }
                }
                ImageView token = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/tokens/token_" + tokenName + ".png")).toURI().toString()));
                token.setFitHeight(15);
                token.setPreserveRatio(true);
                token.setTranslateX((40 * 55) - 84);
                token.setTranslateY((40 * 29) - 36);
                stackPane.getChildren().add(token);
                if (ClientManager.getInstance().getGameState().getFirstPlayer().equals(username)) {
                    ImageView blackToken = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/tokens/token_black.png")).toURI().toString()));
                    blackToken.setFitHeight(15);
                    blackToken.setPreserveRatio(true);
                    blackToken.setTranslateX((40 * 55) - 62);
                    blackToken.setTranslateY((40 * 29) - 36);
                    stackPane.getChildren().add(blackToken);
                }
                pane.getChildren().add(stackPane);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePoints() {
        setPoints(ClientManager.getInstance().getGameState().getScore(), ClientManager.getInstance().getGameState().getTokenColor().toLowerCase());
        for (PlayerState state : ClientManager.getInstance().getGameState().getPlayerStates()) {
            setPoints(state.getScore(), state.getTokenColor().toLowerCase());
        }
    }

    public void turnCard(Card card) {
        for (Node cards : hand.getChildren()) {
            if (cards.getId().equals(String.valueOf(card.getId()))) {
                ImageView imageView = (ImageView) cards;
                String url = imageView.getImage().getUrl();
                if (url.contains("front")) {
                    url = url.replaceAll("front", "back");
                } else if (url.contains("back")) {
                    url = url.replaceAll("back", "front");
                }
                imageView.setImage(new Image(url));
            }
        }
    }

    public void setPoints(int points, String color) {
        List<Node> nodes = tokens.getChildren();
        for (Node node : nodes) {
            if (node.getId() != null && node.getId().contains(color)) {
                node.setVisible(true);
                node.setOpacity(0.8);
                double x = -node.getLayoutX() - node.getBoundsInLocal().getCenterX();
                double y = -node.getLayoutY() - node.getBoundsInLocal().getCenterY();

                switch (points) {
                    case 1:
                        node.setTranslateX(x + 87);
                        node.setTranslateY(y + 335);
                        break;
                    case 2:
                        node.setTranslateX(x + 128.375);
                        node.setTranslateY(y + 335);
                        break;
                    case 3:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 300);
                        break;
                    case 4:
                        node.setTranslateX(x + 108);
                        node.setTranslateY(y + 300);
                        break;
                    case 5:
                        node.setTranslateX(x + 66.5);
                        node.setTranslateY(y + 300);
                        break;
                    case 6:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 300);
                        break;
                    case 7:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 262);
                        break;
                    case 8:
                        node.setTranslateX(x + 66.5);
                        node.setTranslateY(y + 262);
                        break;
                    case 9:
                        node.setTranslateX(x + 108);
                        node.setTranslateY(y + 262);
                        break;
                    case 10:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 262);
                        break;
                    case 11:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 220);
                        break;
                    case 12:
                        node.setTranslateX(x + 108);
                        node.setTranslateY(y + 220);
                        break;
                    case 13:
                        node.setTranslateX(x + 66.5);
                        node.setTranslateY(y + 220);
                        break;
                    case 14:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 220);
                        break;
                    case 15:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 175);
                        break;
                    case 16:
                        node.setTranslateX(x + 66.5);
                        node.setTranslateY(y + 175);
                        break;
                    case 17:
                        node.setTranslateX(x + 108);
                        node.setTranslateY(y + 175);
                        break;
                    case 18:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 175);
                        break;
                    case 19:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 137);
                        break;
                    case 20:
                        node.setTranslateX(x + 87.25);
                        node.setTranslateY(y + 120);
                        break;
                    case 21:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 137);
                        break;
                    case 22:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 99);
                        break;
                    case 23:
                        node.setTranslateX(x + 25.5);
                        node.setTranslateY(y + 61);
                        break;
                    case 24:
                        node.setTranslateX(x + 49.25);
                        node.setTranslateY(y + 30);
                        break;
                    case 25:
                        node.setTranslateX(x + 87.25);
                        node.setTranslateY(y + 24);
                        break;
                    case 26:
                        node.setTranslateX(x + 125);
                        node.setTranslateY(y + 30);
                        break;
                    case 27:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 61);
                        break;
                    case 28:
                        node.setTranslateX(x + 149);
                        node.setTranslateY(y + 99);
                        break;
                    case 29:
                        node.setTranslateX(x + 87.25);
                        node.setTranslateY(y + 70);
                        break;
                    default:
                        node.setTranslateX(x + 46);
                        node.setTranslateY(y + 335);
                }
            }
        }
    }

    private void updateTurn() {
        if (ClientManager.getInstance().getGameState().getActivePlayer().equals(ClientManager.getInstance().getGameState().getUsername())) {
            turn.setText("It's your turn");
        } else {
            turn.setText("It's " + ClientManager.getInstance().getGameState().getActivePlayer() + "'s turn");
        }
    }

    @Override
    public void updateScene(String data) {
        if (data.equals("endTurn")) {
            updateTurn();
        }
        if(data.equals("points")) {
            updatePoints();
        }
        if (data.equals("resources")) {
            updateResources();
        }
    }
}
