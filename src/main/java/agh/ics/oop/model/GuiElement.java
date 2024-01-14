package agh.ics.oop.model;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GuiElement {
    public Circle drawAnimal(double energyratio, int width, int height) {
        Color color = Color.RED;
        if (energyratio > 0.75) {
            color = Color.BLUE;
        } else if (energyratio > 0.25) {
            color = Color.ORANGE;
        }
        return new Circle((double) Math.min(width, height) /2, color);
    }
    public Rectangle drawGrass(int width, int height) {
        return new Rectangle(   width, height, Color.GREEN);
    }
}
