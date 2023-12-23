package agh.ics.oop.model;

import java.util.concurrent.ThreadLocalRandom;

public class Animal implements WorldElement {
    public static final int MAX_ENERGY;
    private final Genotype genotype;
    private final Statistics stats;
    private MapDirection orientation;
    private Vector2d position;
    private Integer energy = MAX_ENERGY;

    protected Animal(Vector2d initialPosition,
                  Genotype genotype,
                  Statistics stats) {
        this.orientation = MapDirection.values()[ThreadLocalRandom.current().nextInt(MapDirection.values().length)];
        this.position = initialPosition;
        this.genotype = genotype;
        this.stats = stats;
    }

    static {
        MAX_ENERGY = Integer.parseInt(System.getProperty("energy", "100"));
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void changeEnergy(int delta) {
        this.energy += delta;
    }

    public Statistics getStats() {
        return this.stats;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(Borders border) {
        this.orientation = this.orientation.next(this.genotype.getMove());
        this.position = border.getPosition(this.position, this.orientation.toUnitVector());
    }
}
