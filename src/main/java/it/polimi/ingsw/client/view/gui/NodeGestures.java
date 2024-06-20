package it.polimi.ingsw.client.view.gui;


import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;

import static java.lang.Math.floor;

class NodeGestures{
    private DragContext nodeDragContext = new DragContext();

    private PannablePane pane;

    private boolean moovable;

    public NodeGestures( PannablePane pane) {
        this.pane = pane;
        this.moovable = true;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() { return onMousePressedEventHandler; }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() { return onMouseDraggedEventHandler; }

    public EventHandler<MouseEvent> getOnMouseReleasedEventHandler() { return onMouseReleasedEventHandler; }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if(!event.isPrimaryButtonDown() || !moovable)
                return;

            nodeDragContext.mouseAnchorX = event.getSceneX();
            nodeDragContext.mouseAnchorY = event.getSceneY();

            Node node = (Node) event.getSource();

            Bloom bloom = new Bloom();
            bloom.setThreshold(0.5);
            node.setEffect(bloom);

//            DropShadow dropShadow = new DropShadow();
//            node.setEffect(dropShadow);

            node.setScaleX(1.07);
            node.setScaleY(1.07);

            nodeDragContext.translateAnchorX = node.getTranslateX();
            nodeDragContext.translateAnchorY = node.getTranslateY();

        }
    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if(!event.isPrimaryButtonDown() || !moovable)
                return;

            double scale = pane.getScale();

            Node node = (Node) event.getSource();

            node.setTranslateX(nodeDragContext.translateAnchorX + (( event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
            node.setTranslateY(nodeDragContext.translateAnchorY + (( event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));

            event.consume();
        }
    };

    private EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event){

            if(event.isSecondaryButtonDown() || !moovable)
                return;

            double scale = pane.getScale();

            Node node = (Node) event.getSource();

            node.setScaleX(1.0);
            node.setScaleY(1.0);

            int squareSize = pane.getSquareSize();

            Bounds bounds = node.getBoundsInParent();

            int gridX = (int) floor(bounds.getCenterX() / (double) squareSize);
            int gridY = (int) floor(bounds.getCenterY() / (double) squareSize);

            int x = (gridX * squareSize) + (squareSize / 2);
            int y = (gridY * squareSize) + (squareSize / 2);

            node.setTranslateX(-node.getLayoutBounds().getCenterX() + x);
            node.setTranslateY(-node.getLayoutBounds().getCenterY() + y);

            Bloom bloom = (Bloom) node.getEffect();
            bloom.setThreshold(1.0);
            node.setEffect(bloom);

//            DropShadow dropShadow = (DropShadow) node.getEffect();
//            dropShadow.setColor(Color.TRANSPARENT);
//            node.setEffect(dropShadow);

            moovable = false;
            event.consume();

        }
    };
}
