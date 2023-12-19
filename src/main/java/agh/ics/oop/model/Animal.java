package agh.ics.oop.model;

import java.util.concurrent.ThreadLocalRandom;

public class Animal implements WorldElement {
    public static final int MAX_ENERGY;
    private MapDirection orientation;
    private Vector2d position;
    private final Genotype genotype;
    private Integer energy = MAX_ENERGY;
    private final Statistics stats;

    public Animal(Vector2d initialPosition,
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

    public Statistics getStats() {
        return this.stats;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public String toString() {
        return this.orientation.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator validator) {
        this.orientation = this.orientation.next(this.genotype.getMove());
        this.position = validator.getPosition(this.position, this.orientation.toUnitVector());
    }
}
