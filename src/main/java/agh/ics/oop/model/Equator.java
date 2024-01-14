package agh.ics.oop.model;

public class Equator extends AbstractGrassPlanter {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    public Equator(Vector2d mapSize) {
        super(mapSize);
        int constraint = (int) (mapSize.getX()*mapSize.getY()*0.20);
        int sum = 0;
        int medium = mapSize.getY()/2 +1;
        int diff = 1;
        int lowerY = medium;
        int upperY = medium;
        while (sum + mapSize.getX()/2 < constraint){
            sum += mapSize.getX();
            if (diff%2 != 0){
                upperY = medium;
            }
            else {
                lowerY = medium;
            }
            for (int i = 1; i <= mapSize.getX(); i++) {
                preferable.put(new Vector2d(i, medium), 0);
            }
            medium += (diff % 2 == 0) ? diff : -diff;
            diff += 1;
        }
        if (sum ==0){
            for (int i = 1; i <= mapSize.getX(); i++) {
                preferable.put(new Vector2d(i, medium), 0);
            }
        }
        this.lowerLeft = new Vector2d(1, lowerY);
        this.upperRight = new Vector2d(mapSize.getX(), upperY);
        for(int i=1; i<=mapSize.getX(); i++){
            for(int j=1; j<lowerY; j++){
                not_preferable.add(new Vector2d(i, j));
            }
        }

        for(int i=1; i<=mapSize.getX(); i++)
            for (int j = upperY + 1; j <= mapSize.getY(); j++) {
                not_preferable.add(new Vector2d(i, j));
            }
    }

    @Override
    public void addPreferable(Vector2d position){
    }

    @Override
    public void eatGrass(Vector2d eatenPosition){
        grasses.remove(eatenPosition);
        if (eatenPosition.follows(lowerLeft) && eatenPosition.precedes(upperRight)) {
            preferable.put(eatenPosition,0);
        }
        else{
            not_preferable.add(eatenPosition);
        }
    }


}
