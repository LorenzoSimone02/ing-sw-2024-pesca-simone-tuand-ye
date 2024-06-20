package it.polimi.ingsw.client.view.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static java.lang.Math.floor;

public class GUI extends Application {

    private Scene scene;

    private HBox root;

    private VBox gameView;
    private VBox boardAndChat;

    private TabPane tables;

        private Tab player;
            private PannablePane playerTable;

        private Tab opponent;
            private PannablePane opponentTable;

    private HBox hand;

    private TabPane boards;
        private Tab playerBoards;
        private HBox hBox;
        private Tab opponentBoards;

        private Button skipOpponentTurn;



    @Override
    public void start(Stage stage) throws IOException {
        newGame(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    private void newGame(Stage stage){
        initialize();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        playerTable.addGrid();
        opponentTable.addGrid();

        if(isTurn()){
            turn();
        } else {
            opponentTurn();
        }
    }

    private void initialize(){
        root = new HBox();

        gameView = new VBox();
        boardAndChat = new VBox();

        root.getChildren().addAll(gameView, boardAndChat);

        createTables(); //Create Players and Opponent Tables

        createHand(); //Create Player hand;

        gameView.getChildren().addAll(tables, hand);

        createBoards();

        //createChat();
        skipOpponentTurn = new Button("Skip Opponent Turn");
        Pane pane = new Pane();
        pane.getChildren().addAll(skipOpponentTurn);
        boardAndChat.getChildren().addAll(boards, pane);
        skipOpponentTurn.setAlignment(Pos.CENTER);

        scene = new Scene(root, 1240, 720);

        addSceneGesture(scene, playerTable);
        addSceneGesture(scene, opponentTable);
    }

    private void createTables(){
            player = new Tab("Player");
            player.setClosable(false);

            opponent = new Tab("Opponent");
            opponent.setClosable(false);

            playerTable = new PannablePane();
            player.setContent(playerTable);

            opponentTable = new PannablePane();
            opponent.setContent(opponentTable);

            tables = new TabPane();
            tables.getTabs().addAll(player, opponent);
            tables.setPrefSize(800,800);
    }

    private void createHand(){
            hand = new HBox(20);

            /*Sostituire con getHand*/
            Image card1 = new Image("test_front.png");
            ImageView card1Hand = new ImageView(card1);
            card1Hand.setFitHeight(100);
            card1Hand.setPreserveRatio(true);

            Image card2 = new Image("test_back.png");
            ImageView card2Hand = new ImageView(card2);
            card2Hand.setFitHeight(100);
            card2Hand.setPreserveRatio(true);

            Image card3 = new Image("test_front.png");
            ImageView card3Hand = new ImageView(card3);
            card3Hand.setFitHeight(100);
            card3Hand.setPreserveRatio(true);
            /**/

            hand.getChildren().addAll(card1Hand, card2Hand, card3Hand);
            hand.setAlignment(Pos.CENTER);
            hand.setMinHeight(120);
            hand.setStyle("-fx-background-color: linear-gradient(to top, #ff0000, #000000);");
    }

    private void createBoards(){
            boards = new TabPane();
            boards.setPrefWidth(450);

            playerBoards = new Tab("Player");
            playerBoards.setClosable(false);

            opponentBoards = new Tab("Opponent");
            opponentBoards.setClosable(false);

            boards.getTabs().addAll(playerBoards, opponentBoards);

            hBox = new HBox(20);
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: brown");

            Image board = new Image("board.png");
            ImageView boardView = new ImageView(board);
            boardView.setFitHeight(350);
            boardView.setPreserveRatio(true);

            VBox deck1 = new VBox(20);

            /**/
            Image card1 = new Image("test_front.png");
            ImageView card1Hand = new ImageView(card1);
            card1Hand.setFitHeight(70);
            card1Hand.setPreserveRatio(true);

            Image card2 = new Image("test_front.png");
            ImageView card2Hand = new ImageView(card2);
            card2Hand.setFitHeight(70);
            card2Hand.setPreserveRatio(true);

            Image card3 = new Image("test_front.png");
            ImageView card3Hand = new ImageView(card3);
            card3Hand.setFitHeight(70);
            card3Hand.setPreserveRatio(true);

            deck1.getChildren().addAll(card1Hand, card2Hand, card3Hand);
            /**/

            VBox deck2 = new VBox(20);

            /**/
            Image card4 = new Image("test_back.png");
            ImageView card4Hand = new ImageView(card1);
            card4Hand.setFitHeight(70);
            card4Hand.setPreserveRatio(true);

            Image card5 = new Image("test_back.png");
            ImageView card5Hand = new ImageView(card2);
            card5Hand.setFitHeight(70);
            card5Hand.setPreserveRatio(true);

            Image card6 = new Image("test_back.png");
            ImageView card6Hand = new ImageView(card3);
            card6Hand.setFitHeight(70);
            card6Hand.setPreserveRatio(true);

            deck2.getChildren().addAll(card4Hand, card5Hand, card6Hand);
            /**/

            hBox.getChildren().addAll(boardView, deck1, deck2);

            deck1.setAlignment(Pos.CENTER);
            deck2.setAlignment(Pos.CENTER);

            playerBoards.setContent(hBox);
    }

    private void createChat(){}

    private void addSceneGesture(Scene scene, PannablePane pannablePane){
        SceneGestures sceneGestures = new SceneGestures(pannablePane);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
    }

    public void turn(){
        placeCard();
    }

    private void placeCard(){
        List<Node> cards = hand.getChildren();
        final boolean[] cardPlaced = {false};

        // Rotate the card and/or add it to table
        for(Node card : cards){
            card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    //Place card
                    if(event.getButton().equals(MouseButton.PRIMARY)){
                        if(cardPlaced[0]){
                            return;
                        }
                        addCard(playerTable, (ImageView) card);
                        hand.getChildren().removeAll(card);
                        cardPlaced[0] = true;
                        System.out.println(cardPlaced[0]);
                        event.consume();
                        if(playerTable.getChildren().getLast() instanceof ImageView)
                        {
                            ImageView placedCard = (ImageView) playerTable.getChildren().getLast();

                            NodeGestures nodeGestures = new NodeGestures(playerTable);

                            DragContext nodeDragContext = new DragContext();

                            final boolean[] moovable = {true};

                            placedCard.setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override public void handle(MouseEvent event) {
                                    // left mouse button => dragging
                                    if(!event.isPrimaryButtonDown() || !moovable[0])
                                        return;

                                    nodeDragContext.mouseAnchorX = event.getSceneX();
                                    nodeDragContext.mouseAnchorY = event.getSceneY();

                                    Node node = (Node) event.getSource();

                                    Bloom bloom = new Bloom();
                                    bloom.setThreshold(0.5);
                                    node.setEffect(bloom);

                                    //DropShadow dropShadow = new DropShadow();
                                    //node.setEffect(dropShadow);

                                    node.setScaleX(1.07);
                                    node.setScaleY(1.07);

                                    nodeDragContext.translateAnchorX = node.getTranslateX();
                                    nodeDragContext.translateAnchorY = node.getTranslateY();
                                }
                            });

                            placedCard.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                @Override public void handle(MouseEvent event) {
                                    // left mouse button => dragging
                                    if(!event.isPrimaryButtonDown() || !moovable[0])
                                        return;

                                    double scale = playerTable.getScale();

                                    Node node = (Node) event.getSource();

                                    node.setTranslateX(nodeDragContext.translateAnchorX + (( event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
                                    node.setTranslateY(nodeDragContext.translateAnchorY + (( event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));

                                    event.consume();
                                }
                            });

                            placedCard.setOnMouseReleased(new EventHandler<MouseEvent>() {
                                @Override public void handle(MouseEvent event) {
                                    if(event.isSecondaryButtonDown() || !moovable[0])
                                        return;



                                    double scale = playerTable.getScale();

                                    Node node = (Node) event.getSource();

                                    node.setScaleX(1.0);
                                    node.setScaleY(1.0);

                                    int squareSize = playerTable.getSquareSize();

                                    Bounds bounds = node.getBoundsInParent();

                                    int gridX = (int) floor(bounds.getCenterX() / (double) squareSize);
                                    int gridY = (int) floor(bounds.getCenterY() / (double) squareSize);

                                    int matrixX = (int)Math.round((float)gridX/2.0); /*index for server matrix*/
                                    int matrixY = (int)Math.round(gridY - 40.0); /*index for server matrix*/

                                    int x;
                                    if(gridX % 2 == 0){
                                        x = (gridX * squareSize) + (squareSize / 2);
                                    } else {
                                        x = ((int)Math.round(gridX/2.0)* 2 * squareSize) + (squareSize / 2);
                                    }

                                    int y = (gridY * squareSize) + (squareSize / 2);

                                    node.setTranslateX(-node.getLayoutBounds().getCenterX() + x);
                                    node.setTranslateY(-node.getLayoutBounds().getCenterY() + y);

                                    Bloom bloom = (Bloom) node.getEffect();
                                    bloom.setThreshold(1.0);
                                    node.setEffect(bloom);

                                    //DropShadow dropShadow = (DropShadow) node.getEffect();
                                    //dropShadow.setColor(Color.TRANSPARENT);
                                    //node.setEffect(dropShadow);

                                    moovable[0] = false;
                                    event.consume();
                                    draw();
                                }
                            });
                        }
                    }
                    //Turn card
                    else if(event.getButton().equals(MouseButton.SECONDARY)){
                        ImageView imageView = (ImageView) card;
                        Image image = imageView.getImage();
                        String url = image.getUrl();
                        System.out.println(url);
                        if(url.contains("front"))
                        {
                            url = url.replaceAll("front","back");
                        } else if(url.contains("back")){
                            url = url.replaceAll("back","front");
                        }
                        System.out.println(url);
                        imageView.setImage(new Image(url));
                        event.consume();
                    }
                }
            });
        }


    }

    private void draw(){
        final boolean[] drawn = {false};
        List<Node> nodes = hBox.getChildren();
        for(Node node : nodes){
            if(node instanceof VBox && !drawn[0]){
                List<Node> cards = ((VBox) node).getChildren();
                for(Node card : cards){
                    if(card instanceof ImageView){
                        card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override public void handle(MouseEvent event) {
                                if(event.getButton().equals(MouseButton.SECONDARY)){
                                    ImageView imageView = (ImageView) card;
                                    Image image = imageView.getImage();
                                    String url = image.getUrl();
                                    System.out.println(url);
                                    if(url.contains("front"))
                                    {
                                        url = url.replaceAll("front","back");
                                    } else if(url.contains("back")){
                                        url = url.replaceAll("back","front");
                                    }
                                    System.out.println(url);
                                    imageView.setImage(new Image(url));
                                    event.consume();
                                } else if(event.getButton().equals(MouseButton.PRIMARY)){
                                    if(!drawn[0]) {
                                        ImageView imageView = new ImageView(((ImageView) card).getImage());
                                        hand.getChildren().add(imageView);
                                        imageView.setFitHeight(100);
                                        imageView.setPreserveRatio(true);

                                        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                            @Override public void handle(MouseEvent event) {
                                                if(event.getButton().equals(MouseButton.SECONDARY)){
                                                    Image image = imageView.getImage();
                                                    String url = image.getUrl();
                                                    System.out.println(url);
                                                    if(url.contains("front"))
                                                    {
                                                        url = url.replaceAll("front","back");
                                                    } else if(url.contains("back")){
                                                        url = url.replaceAll("back","front");
                                                    }
                                                    System.out.println(url);
                                                    imageView.setImage(new Image(url));
                                                }
                                            }
                                        });

                                        ((VBox) node).getChildren().remove(card);
                                        drawn[0] = true;
                                        opponentTurn();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private void opponentTurn(){
        skipOpponentTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                turn();
                event.consume();
            }
        });
    }

    private void addCard(PannablePane pane, ImageView node){
        ImageView card = new ImageView(node.getImage());
        playerTable.getChildren().add(card);
        card.setFitWidth(26.5);
        card.setPreserveRatio(true);
        card.setX(((80 * playerTable.getSquareSize()) + playerTable.getSquareSize()/2.0) - (card.getBoundsInLocal().getWidth()/2.0));
        card.setY(((80 * playerTable.getSquareSize()) + playerTable.getSquareSize()/2.0) - (card.getBoundsInLocal().getHeight()/2.0));
    }

    void setCardPlaced(boolean cardPlaced){ cardPlaced = true; }

    public boolean isTurn(){return true;} /*Change with get player turn*/
}