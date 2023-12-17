package agh.ics.oop.model;

import java.util.concurrent.ThreadLocalRandom;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private final Genotype genotype;
    private Integer energy;

    public Animal(Vector2d initialPosition, int energy, Genotype genotype) {
        this.orientation = MapDirection.values()[ThreadLocalRandom.current().nextInt(MapDirection.values().length)];
        this.position = initialPosition;
        this.genotype = genotype;
        this.energy = energy;
    }

    public Animal(Vector2d initialPosition, Genotype genotype, int energy) {
        this.orientation = MapDirection.values()[ThreadLocalRandom.current().nextInt(MapDirection.values().length)];
        this.position = initialPosition;
        this.genotype = genotype;
        this.energy = energy;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public int getEnergy() {
        return this.energy;
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
