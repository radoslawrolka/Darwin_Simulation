package agh.ics.oop.model;

public class Globe implements Borders<Vector2d>{
    private final Vector2d mapSize;


    public Globe(Vector2d mapSize){
        this.mapSize = mapSize;
    }

    @Override
    public Vector2d getPosition(Vector2d position, Vector2d move) {
        Vector2d newPosition = position.add(move);
        if (newPosition.follows(new Vector2d(1,1)) && newPosition.precedes(mapSize)){
            return newPosition;
        }

        else if (newPosition.getY() <= 0 || newPosition.getY() > mapSize.getY()){
            return position;
        }
        else if (newPosition.getX() > mapSize.getX()){
            return new Vector2d(1, newPosition.getY());
        }
        else{
            return new Vector2d(mapSize.getX(), newPosition.getY());
        }
    }
}
