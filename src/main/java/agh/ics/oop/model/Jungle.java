package agh.ics.oop.model;

public class Jungle extends AbstractGrassPlanter {

    private final int constraint;
    public Jungle(Vector2d mapSize) {
        super(mapSize);
        constraint = (int) (mapSize.getX()*mapSize.getY()*0.23);
        for(int i=0; i<mapSize.getX(); i++){
            for(int j=0; j<mapSize.getY(); j++){
                super.not_preferable.add(new Vector2d(i, j));
            }
        }
    }
    @Override
    public void addPreferable(Vector2d position) {
        if (preferable.size() > constraint) {
            return;
        }
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
        System.out.println(preferable.containsKey(new Vector2d(1,2)));
        if (isPreferable == 0) {
            not_preferable.add(eatenPosition);
        }
        else {
            preferable.put(eatenPosition, isPreferable);
        }


    }
}