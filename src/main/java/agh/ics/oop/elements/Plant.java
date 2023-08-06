package agh.ics.oop.elements;

import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.IElement;

public class Plant implements IElement {
    private final Vector2d position;

    public Plant(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    //------------------------------------
    @Override
    public String getImagePath(){
        return null;
    }

}
