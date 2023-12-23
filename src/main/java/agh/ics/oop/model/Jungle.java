package agh.ics.oop.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Jungle extends AbstractGrassPlanter {


    public Jungle(Vector2d mapSize) {
        super(mapSize);
        for(int i=0; i<mapSize.getX(); i++){
            for(int j=0; j<mapSize.getY(); j++){
                super.not_preferable.add(new Vector2d(i, j));
            }
        }
    }
    @Override
    public void addPreferable(Vector2d position) {
        for (MapDirection direction : MapDirection.values()) {
            Vector2d newPosition = position.add(direction.toUnitVector());
            if (super.checkAvailability(newPosition) && !grasses.containsKey(newPosition)) {
                preferable.merge(newPosition, 1, Integer::sum);
            }
        }
    }

    @Override
    public void eatGrass(Vector2d eatenPosition) {
        grasses.remove(eatenPosition);
        for (MapDirection direction : MapDirection.values()) {
            Vector2d newPosition = eatenPosition.add(direction.toUnitVector());
            if (super.checkAvailability(newPosition) && !grasses.containsKey(newPosition)) {
                if (preferable.containsKey(newPosition)) {
                    preferable.put(newPosition, preferable.get(newPosition) - 1);
                }
            }
        }
    }
}







