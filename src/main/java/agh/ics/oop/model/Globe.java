package agh.ics.oop.model;

import java.util.Vector;

public class Globe implements Borders<Vector2d>{

    private Vector2d mapSize;


    public Globe(Vector2d mapSize){
        this.mapSize = mapSize;
    }

    @Override
    public Vector2d getPosition(Vector2d position, MapDirection orientation) {
        Vector2d newPosition = position.add(orientation.toUnitVector());
        if (newPosition.follows(new Vector2d(0,0)) && newPosition.precedes(mapSize)){
            return newPosition;
        }
        else if (newPosition.getX() < 0){
            return new Vector2d(mapSize.getX()-1, newPosition.getY());
        }
        else if (newPosition.getX() > mapSize.getX()){
            return new Vector2d(0, newPosition.getY());
        }
        else{
            orientation.next(4);
            return position;
        }
    }
}
