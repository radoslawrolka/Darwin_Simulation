package agh.ics.oop.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGrassPlanter implements GrassPlanter {
    protected final Map<Vector2d, Integer> preferable = new HashMap<>();
    protected final Map<Vector2d,Grass> grasses = new HashMap<>();
    protected final Set<Vector2d> not_preferable = new HashSet<>();
    private final Vector2d mapSize;

    public AbstractGrassPlanter(Vector2d mapSize) {
        this.mapSize = mapSize;
    }

    @Override
    public void plantGrass(int grassNumber) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < grassNumber; i++) {
            if (!preferable.isEmpty() && !not_preferable.isEmpty()) {
                Collection<Vector2d> positions = random.nextInt(5) != 0 ? preferable.keySet() : not_preferable;
                plantGrassAtRandomPosition(positions);
            }
            else if (!preferable.isEmpty()){
                plantGrassAtRandomPosition(preferable.keySet());
            }
            else if (!not_preferable.isEmpty()){
                plantGrassAtRandomPosition(not_preferable);
            }
            else {
                break;
            }
        }
    }

    public Grass grassAtPosition(Vector2d position) {
        return grasses.get(position);
    }

    public void plantGrassAtRandomPosition(Collection<Vector2d> positions) {
        int randomIndex = ThreadLocalRandom.current().nextInt(positions.size());
        
        Iterator<Vector2d> iterator = positions.iterator();
        for (int k = 0; iterator.hasNext(); k++) {
            Vector2d position = iterator.next();
            if (k == randomIndex) {
                grasses.put(position, new Grass(position));
                iterator.remove();
                addPreferable(position);
                break;
            }
        }
    }

    public abstract void addPreferable(Vector2d position);

    public boolean checkAvailability(Vector2d position) {
        return position.follows(new Vector2d(0, 0)) && position.precedes(mapSize);
    }

    public abstract void eatGrass(Vector2d eatenPosition);
}
