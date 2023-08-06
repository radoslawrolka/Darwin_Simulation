package agh.ics.oop.map;

import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.Animal;

import java.util.Random;

public class HellPortal implements IMapBorder{
    private final int width;
    private final int height;
    private final int penalty;

    public HellPortal(int width, int height, int penalty) {
        this.width = width;
        this.height = height;
        this.penalty = penalty;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getX() > 0 && position.getX() <= width && position.getY() > 0 && position.getY() <= height;
    }
    @Override
    public void correctPosition(Animal animal) {
        Random rand = new Random();
        int x = rand.nextInt(this.width) + 1;
        int y = rand.nextInt(this.height) + 1;
        animal.setPosition(new Vector2d(x, y));
        animal.removeEnergy(penalty);
    }
}
