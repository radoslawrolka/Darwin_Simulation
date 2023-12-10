package agh.ics.oop.model;

public class Jungle extends AbstractWorldMap{

    int size;

    public Jungle(int size){
        super();
        this.size = size;
    }

    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0,0), new Vector2d(size, size));
    }
}
