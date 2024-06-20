package it.polimi.ingsw.client.view.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PannablePane extends Pane {

    DoubleProperty scale = new SimpleDoubleProperty(10.0);

    private Rectangle[][] grid;

    private int size;

    private int spots = 160;

    private int squareSize;

    public int getSquareSize() {
        this.size = (int) getSize();
        this.squareSize = (int) size/spots;
        return squareSize;
    }

    public int getSize(){
        return (int) getMinHeight();
    }

    public int getSpots(){
        return spots;
    }

    public PannablePane() {
        setMinSize(1600, 1600);
        setStyle("-fx-background-color: #441e1e;");

        scaleXProperty().bind(scale);
        scaleYProperty().bind(scale);
    }

    public void addGrid(){
        double w = getMinWidth();
        double h = getMinHeight();

        // add grid
        Canvas grid = new Canvas(w, h);

        // don't catch mouse events
        grid.setMouseTransparent(true);

        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.5);

        // draw grid lines
        double offset = w / (double)spots;
        for( double i=offset; i < w; i+=offset) {
            gc.strokeLine( i, 0, i, h);
            gc.strokeLine( 0, i, w, i);
        }

        getChildren().add(grid);

        grid.toBack();
    }

    public double getScale() {
        return scale.get();
    }

    public void setScale(double scale) {
        this.scale.set(scale);
    }

    public void setPivot(double x, double y) {
        setTranslateX(getTranslateX() - x);
        setTranslateY(getTranslateY() - y);
    }
}

