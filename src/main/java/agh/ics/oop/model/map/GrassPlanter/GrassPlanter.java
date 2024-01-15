package agh.ics.oop.model.map.GrassPlanter;

import agh.ics.oop.model.entities.grass.Grass;
import agh.ics.oop.model.map.utilities.Vector2d;

import java.util.List;

public interface GrassPlanter {

    void plantGrass(int grassNumber);

    void eatGrass(Vector2d eatenPositions);

    Grass grassAtPosition(Vector2d position);

    int getGrassNumber();

    List<Vector2d> getPrefferredPositions();

}
