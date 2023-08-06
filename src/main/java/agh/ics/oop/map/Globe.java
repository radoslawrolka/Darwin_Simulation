package agh.ics.oop.map;

import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.Animal;

public class Globe implements IMapBorder{
    private final int width;
    private final int height;

    public Globe(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return position.getX() > 0 && position.getX() <= this.width && position.getY() > 0 && position.getY() <= this.height;
    }
    @Override
    public void correctPosition(Animal animal) {
        int x = animal.getPosition().getX();
        int y = animal.getPosition().getY();
        if (x == 0) {
            if (y == 0) {animal.setPosition(new Vector2d(this.width, this.height)); }
            else if (y == this.height + 1) {animal.setPosition(new Vector2d(this.width, 1)); }
            else {animal.setPosition(new Vector2d(this.width, y)); }
        }
        else if (x == this.width + 1) {
            if (y == 0) {animal.setPosition(new Vector2d(1, this.height)); }
            else if (y == this.height + 1) {animal.setPosition(new Vector2d(1, 1)); }
            else {animal.setPosition(new Vector2d(1, y)); }
        }
        else if (y == 0) {animal.setPosition(new Vector2d(x, this.height)); }
        else {animal.setPosition(new Vector2d(x, 1)); }
    }
}
