package agh.ics.oop.map;

import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.Animal;

public interface IMapBorder {
    boolean canMoveTo(Vector2d position);
    void correctPosition(Animal animal);
}
