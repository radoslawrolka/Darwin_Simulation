package agh.ics.oop.model.entities.grass;

import agh.ics.oop.model.map.utilities.Vector2d;
import agh.ics.oop.model.entities.WorldElement;

public class Grass implements WorldElement {
    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
