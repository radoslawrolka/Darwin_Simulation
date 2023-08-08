package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

import java.util.Random;

public class ForestedEquator implements IMapGrounds {
    private final Random random = new Random();
    private final Vector2d equatorLowerLeft;
    private final Vector2d equatorUpperRight;
    private final int mapWidth;
    private final int mapHeight;

    public ForestedEquator(int equatorWidth, int equatorHeight, int mapWidth, int mapHeight) {
        this.equatorLowerLeft = new Vector2d((mapWidth-equatorWidth)/2+1, (mapHeight-equatorHeight)/2+1);
        this.equatorUpperRight = new Vector2d((equatorLowerLeft.getX()+equatorWidth-1), (equatorLowerLeft.getY()+equatorHeight-1));
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }
    @Override
    public boolean isPreferable(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return equatorLowerLeft.precedes(position) && equatorUpperRight.follows(position);
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

    @Override
    public String groundType(Vector2d position) {
        if (isPreferable(position)) {
            return "equator";
        }
        else {
            return "grass";
        }
    }
}
