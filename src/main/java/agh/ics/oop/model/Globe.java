package agh.ics.oop.model;

import java.util.Vector;

public class Globe implements Borders<Vector2d>{

    private Vector2d mapSize;


    public Globe(Vector2d mapSize){
        this.mapSize = mapSize;
    }

    @Override
    public Vector2d getPosition(Vector2d position, Vector2d move) {
        System.out.println(position.toString());
        Vector2d newPosition = position.add(move);
        System.out.println(newPosition.toString());
        if (newPosition.follows(new Vector2d(0,0)) && newPosition.precedes(mapSize.subtract(new Vector2d(1,1)))){
            return newPosition;
        }

        else if (newPosition.getY() < 0 || newPosition.getY() > mapSize.getY()-1){
            return position;
        }
        else if (newPosition.getX() > mapSize.getX()){
            return new Vector2d(0, newPosition.getY());
        }
        else{
            return new Vector2d(mapSize.getX()-1, newPosition.getY());
        }
    }
}
