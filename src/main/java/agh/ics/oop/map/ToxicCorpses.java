package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

import java.util.Random;

public class ToxicCorpses implements IMapGrounds{
    private final Random random = new Random();
    private final int mapWidth;
    private final int mapHeight;
    private final World map;

    public ToxicCorpses(int mapWidth, int mapHeight, World map) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = map;
    }

    @Override
    public boolean isPreferable(Vector2d position) {
        return !map.isDeadHere(position);
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
        if (map.isDeadHere(position)) {
            return "toxic";
        }
        else {
            return "grass";
        }
    }

}
