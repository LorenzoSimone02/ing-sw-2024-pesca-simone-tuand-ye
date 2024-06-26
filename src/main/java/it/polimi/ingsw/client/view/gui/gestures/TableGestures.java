package it.polimi.ingsw.client.view.gui.gestures;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class TableGestures {

    private static final double MAX_SCALE = 5;
    private static final double MIN_SCALE = 1;
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double translateAnchorX;
    private double translateAnchorY;
    private final Pane pane;

    public TableGestures(Pane pane) {
        this.pane = pane;

        Rectangle clipRect = new Rectangle();
        clipRect.widthProperty().bind(pane.widthProperty());
        clipRect.heightProperty().bind(pane.heightProperty());
        pane.setClip(clipRect);

        pane.setOnMousePressed(onMousePressedEventHandler);
        pane.setOnMouseDragged(onMouseDraggedEventHandler);
        pane.setOnScroll(onScrollEventHandler);
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {
        public void handle(MouseEvent event) {

            if (!event.isSecondaryButtonDown())
                return;
            mouseAnchorX = event.getSceneX();
            mouseAnchorY = event.getSceneY();

            translateAnchorX = pane.getTranslateX();
            translateAnchorY = pane.getTranslateY();

        }
    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<>() {
        public void handle(MouseEvent event) {
            if (!event.isSecondaryButtonDown())
                return;

            double newTranslateX = translateAnchorX + event.getSceneX() - mouseAnchorX;
            double newTranslateY = translateAnchorY + event.getSceneY() - mouseAnchorY;

            pane.setTranslateX(newTranslateX);
            pane.setTranslateY(newTranslateY);

            event.consume();
        }
    };

    private final EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<>() {
        @Override
        public void handle(ScrollEvent event) {
            double delta = 1.07d;

            double scale = pane.getScaleX();
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else if (event.getDeltaY() > 0)
                scale *= delta;

            if (scale < MIN_SCALE || scale > MAX_SCALE)
                return;

            double f = (scale / oldScale) - 1;

            double dx = (event.getSceneX() - (pane.getBoundsInParent().getWidth() / 2 + pane.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (pane.getBoundsInParent().getHeight() / 2 + pane.getBoundsInParent().getMinY()));

            setScale(scale);
            setPivot(f * dx, f * dy);

            event.consume();
        }
    };

    public void setScale(double scale) {
        pane.setScaleX(scale);
        pane.setScaleY(scale);
    }

    public void setPivot(double x, double y) {
        pane.setTranslateX(pane.getTranslateX() - x);
        pane.setTranslateY(pane.getTranslateY() - y);
    }
}