package agh.ics.oop.model;

public class Equator extends AbstractGrassPlanter {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    public Equator(Vector2d mapSize, int lowerY, int upperY) {
        super(mapSize);
        this.lowerLeft = new Vector2d(0, lowerY);
        this.upperRight = new Vector2d(mapSize.getX(), upperY);
        for (int i = 0; i <= mapSize.getX(); i++) {
            for (int j = lowerY; j <= upperY; j++) {
                preferable.put(new Vector2d(i, j), 0);
            }
        }
        for(int i=0; i<= mapSize.getX(); i++){
            for(int j=0; j<lowerY; j++){
                not_preferable.add(new Vector2d(i, j));
            }
        }

        for(int i=0; i<= mapSize.getX(); i++){
            for(int j=upperY+1; j<=mapSize.getY(); j++){
                not_preferable.add(new Vector2d(i, j));
            }
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
