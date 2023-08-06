package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

import java.util.Random;

public class ToxicCorpses implements IMapGrounds{
    private final Random random = new Random();
    private final int mapWidth;
    private final int mapHeight;
    private final Cementary cementary;

    public ToxicCorpses(int mapWidth, int mapHeight, Cementary cementary) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cementary = cementary;
    }

    @Override
    public Vector2d choosePosition() {
        int x = random.nextInt(mapWidth) +1;
        int y = random.nextInt(mapHeight) +1;
        return new Vector2d(x, y);
    }

    @Override
    public boolean isPreferable(Vector2d position) {
        return cementary.isEmpty(position);
    }

    @Override
    public boolean willBePlanted(Vector2d position) {
        if (isPreferable(position)) {
            return random.nextInt(5) != 4;
        }
        else {
            return random.nextInt(5) == 4;
        }
    }
}
