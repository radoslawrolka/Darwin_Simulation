package agh.ics.oop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface GrassPlanter {

    void plantGrass(int grassNumber);

    void eatGrass(Vector2d eatenPositions);

    Grass grassAtPosition(Vector2d position);

    int getGrassNumber();

    List<Vector2d> getPrefferredPositions();

}
