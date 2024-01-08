package agh.ics.oop.model;

import java.util.HashMap;

public interface GrassPlanter {

    void plantGrass(int grassNumber);

    void eatGrass(Vector2d eatenPositions);

    Grass grassAtPosition(Vector2d position);

    int getGrassNumber();

}
