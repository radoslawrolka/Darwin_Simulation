package agh.ics.oop.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Equator implements GrassGrow {

    private List<Vector2d> preferable = new LinkedList<>();
    private List<Vector2d> not_preferable = new LinkedList<>();

    private Vector2d mapSize;

    private Vector2d lowerLeft;
    private Vector2d upperRight;

    public Equator(Vector2d mapSize, Boundary preferredBoundary){
        this.mapSize = mapSize;
        this.lowerLeft = preferredBoundary.lowerLeft();
        this.upperRight = preferredBoundary.upperRight();
        IntStream.range(lowerLeft.getX(), upperRight.getX()+1)
                .forEach(i ->
                        IntStream.range(lowerLeft.getY(), upperRight.getY()+1)
                                .forEach(j -> preferable.add(new Vector2d(i, j)))
                );
        IntStream.range(0,lowerLeft.getX())
                .forEach(i ->
                        IntStream.range(0, lowerLeft.getY())
                                .forEach(j -> not_preferable.add(new Vector2d(i, j)))
                );

        IntStream.range(upperRight.getX()+1, mapSize.getX())
                .forEach(i ->
                        IntStream.range(upperRight.getY()+1, mapSize.getY())
                                .forEach(j -> not_preferable.add(new Vector2d(i, j)))
                );
    }

    @Override
    public void plantGrass(int grassNumber, HashMap<Vector2d,Grass> grasses){
        Random random = new Random();
        int size_preferable = preferable.size();
        int size_not_preferable = not_preferable.size();
        for(int i=0; i<grassNumber; i++){
            if(random.nextInt(5) != 0 && size_preferable > 0){
                int randomNumber1 = random.nextInt(size_preferable);
                Vector2d position = preferable.get(randomNumber1);
                grasses.put(position, new Grass(position));
                preferable.remove(randomNumber1);//TODO: Collections.shuffle(preferable);
                size_preferable -=1;
            }
            else if(size_not_preferable > 0){
                int randomNumber1 = random.nextInt(size_not_preferable);
                Vector2d position = not_preferable.get(randomNumber1);
                grasses.put(position, new Grass(position));
                not_preferable.remove(randomNumber1);
                size_not_preferable -=1;

            }

        }

    }

    @Override
    public void eatGrass(List<Vector2d> eatenPositions, HashMap<Vector2d,Grass> grasses){
        for(Vector2d position: eatenPositions){
            grasses.remove(position);
            if (position.follows(lowerLeft) && position.precedes(upperRight)){
                preferable.add(position);
            }
            else{
                not_preferable.add(position);
            }
        }
    }


}
