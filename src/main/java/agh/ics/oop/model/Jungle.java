package agh.ics.oop.model;

public class Jungle extends AbstractGrassPlanter {
    public Jungle(Vector2d mapSize) {
        super(mapSize);
        for(int i=1; i<=mapSize.getX(); i++){
            for(int j=1; j<=mapSize.getY(); j++){
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
        int isPreferable = 0;
        for (MapDirection direction : MapDirection.values()) {
            Vector2d newPosition = eatenPosition.add(direction.toUnitVector());
            if (super.checkAvailability(newPosition)) {
                if (!grasses.containsKey(newPosition)) {
                    preferable.compute(newPosition, (key, value) -> (value > 1) ? value - 1 : null);
                    if (preferable.get(newPosition) == null) {
                        not_preferable.add(newPosition);
                    }
                }
                else {
                    isPreferable += 1;
                }
            }
        }
        if (isPreferable == 0) {
            not_preferable.add(eatenPosition);
        }
        else {
            preferable.put(eatenPosition, isPreferable);
        }


    }
}