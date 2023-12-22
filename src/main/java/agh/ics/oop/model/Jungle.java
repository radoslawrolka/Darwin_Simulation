package agh.ics.oop.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Jungle implements GrassGrow {

    private Map<Vector2d, Integer> preferable = new HashMap<>();
    private Set<Vector2d> not_preferable = new HashSet<>();

    private Vector2d mapSize;

    public Jungle(Vector2d mapSize) {
        this.mapSize = mapSize;
        for(int i=0; i<mapSize.getX(); i++){
            for(int j=0; j<mapSize.getY(); j++){
                not_preferable.add(new Vector2d(i, j));
            }
        }
    }


    @Override
    public void plantGrass(int grassNumber, HashMap<Vector2d, Grass> grasses) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (preferable.size() > 0 && not_preferable.size() > 0) {
            for (int i = 0; i < grassNumber; i++) {
                if (random.nextInt(5) != 0) {
                    int randomIndex = random.nextInt(preferable.size());
                    int k = 0;
                    for (Vector2d position : preferable.keySet()) {
                        if (k == randomIndex) {
                            grasses.put(position, new Grass(position));
                            preferable.remove(position);
                            addPreferable(position, grasses);
                            break;
                        }
                        k++;
                    }
                } else {
                    int randomIndex = random.nextInt(not_preferable.size());
                    int k = 0;
                    for (Vector2d position : not_preferable) {
                        if (k == randomIndex) {
                            grasses.put(position, new Grass(position));
                            not_preferable.remove(position);
                            addPreferable(position, grasses);
                            break;
                        }
                        k++;
                    }
                }
            }
        }
        else if (preferable.size() > 0) {
                for (int i = 0; i < grassNumber; i++) {
                    int randomIndex = random.nextInt(preferable.size());
                    int k = 0;
                    for (Vector2d position : preferable.keySet()) {
                        if (k == randomIndex) {
                            grasses.put(position, new Grass(position));
                            preferable.remove(position);
                            addPreferable(position, grasses);
                            break;
                        }
                        k++;
                    }
                }
            } else if (not_preferable.size() > 0) {
                for (int i = 0; i < grassNumber; i++) {
                    int randomIndex = random.nextInt(not_preferable.size());
                    int k = 0;
                    for (Vector2d position : not_preferable) {
                        if (k == randomIndex) {
                            grasses.put(position, new Grass(position));
                            not_preferable.remove(position);
                            addPreferable(position, grasses);
                            break;
                        }
                        k++;
                    }
                }
            }
        }
    }

    private void addPreferable(Vector2d position, HashMap<Vector2d, Grass> grasses,) {
        for (MapDirection direction : MapDirection.values()) {
            Vector2d newPosition = position.add(direction.toUnitVector());
            if (checkAvailability(newPosition) && !grasses.containsKey(newPosition)) {
                if (preferable.containsKey(newPosition)) {
                    preferable.put(newPosition, preferable.get(newPosition) + 1);
                } else {
                    preferable.put(newPosition, 1);
                }
            }
        }


    }

    private boolean checkAvailability(Vector2d position) {
        if (position.follows(new Vector2d(0, 0)) && position.precedes(mapSize)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void eatGrass(Vector2d eatenPosition, HashMap<Vector2d, Grass> grasses) {
        grasses.remove(eatenPosition);
        for (MapDirection direction : MapDirection.values()) {
            Vector2d newPosition = eatenPosition.add(direction.toUnitVector());
            if (checkAvailability(newPosition) && !grasses.containsKey(newPosition)) {
                if (preferable.get(newPosition) > 1) {
                    preferable.put(newPosition, preferable.get(newPosition) - 1);
                } else {
                    preferable.remove(newPosition);
                }
            }
        }
    }
}







