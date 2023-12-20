package agh.ics.oop.model;

import java.util.*;
import java.util.stream.IntStream;

public class Jungle implements GrassGrow {

    private List<Vector2d> preferable = new LinkedList<>();
    private List<Vector2d> not_preferable = new LinkedList<>();

    private Vector2d mapSize;

    public Jungle(Vector2d mapSize) {
        this.mapSize = mapSize;
        IntStream.range(0, mapSize.getX() + 1)
                .forEach(i ->
                        IntStream.range(0, mapSize.getY() + 1)
                                .forEach(j -> not_preferable.add(new Vector2d(i, j)))
                );
    }


    @Override
    public void plantGrass(int grassNumber, HashMap<Vector2d, Grass> grasses) {
        Random random = new Random();
        for (int i = 0; i < grassNumber; i++) {
            int size_preferable = preferable.size();
            int size_not_preferable = not_preferable.size();
            if (random.nextInt(5) != 0 && size_preferable > 0) {
                int randomNumber1 = random.nextInt(size_preferable);
                Vector2d position = preferable.get(randomNumber1);
                grasses.put(position, new Grass(position));
                preferable.remove(randomNumber1);//TODO: Collections.shuffle(preferable);
                addPreferable(position, grasses);
            } else if (size_not_preferable > 0) {
                int randomNumber1 = random.nextInt(size_not_preferable);
                Vector2d position = not_preferable.get(randomNumber1);
                grasses.put(position, new Grass(position));
                not_preferable.remove(randomNumber1);
                addPreferable(position, grasses);
            }

        }

    }

    private void addPreferable(Vector2d position, HashMap<Vector2d, Grass> grasses) {
        for (MapDirection direction : MapDirection.values()) {
            Vector2d newPosition = position.add(direction.toUnitVector());
            if (checkAvailability(newPosition) && !grasses.containsKey(newPosition)) {
                preferable.add(newPosition);
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
    public void eatGrass(List<Vector2d> eatenPositions, HashMap<Vector2d, Grass> grasses) {
        for (Vector2d position : eatenPositions) {
            grasses.remove(eatenPositions);
            if (checkAvailability(position) && grasses.containsKey(position)) {
                preferable.add(position);
            }
            else if (checkAvailability(position) && !grasses.containsKey(position)) {
                not_preferable.add(position);
            }
        }

    }
}







