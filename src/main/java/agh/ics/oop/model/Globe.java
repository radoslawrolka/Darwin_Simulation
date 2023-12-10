package agh.ics.oop.model;

public class Globe extends AbstractWorldMap{

    private Vector2d size;

    public Globe(Vector2d size){
        super();
        this.size = size;
    }
    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0,0), size);
    }
}
