package agh.ics.oop.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface GrassGrow {

    void plantGrass(int grassNumber, HashMap<Vector2d,Grass> grasses);

    void eatGrass(List<Vector2d> eatenPositions, HashMap<Vector2d,Grass> grasses);

}
