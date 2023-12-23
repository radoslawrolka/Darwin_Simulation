package agh.ics.oop.model;

import java.util.concurrent.ThreadLocalRandom;

public class Animal implements WorldElement {
    private final int MAX_ENERGY;
    private final Genotype genotype;
    private final Statistics stats;
    private MapDirection orientation;
    private Vector2d position;
    private Integer energy;

    public Animal(Vector2d initialPosition,
                  Genotype genotype,
                  Statistics stats,
                  int maxEnergy,
                  int startEnergy) {
        this.orientation = MapDirection.values()[ThreadLocalRandom.current().nextInt(MapDirection.values().length)];
        this.position = initialPosition;
        this.genotype = genotype;
        this.stats = stats;
        this.MAX_ENERGY = maxEnergy;
        this.energy = startEnergy;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void changeEnergy(int delta) {
        this.energy = Math.min(this.energy+delta, MAX_ENERGY);
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

    public void move(Borders<Vector2d> validator) {
        this.orientation = this.orientation.next(this.genotype.getMove());
        this.position = validator.getPosition(this.position, this.orientation.toUnitVector());
        // this.energy -= this.moveEnergy;
    }
}
