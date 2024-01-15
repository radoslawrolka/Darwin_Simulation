package agh.ics.oop.model.entities.animal;

import agh.ics.oop.model.entities.WorldElement;
import agh.ics.oop.model.entities.animal.genotype.Genotype;
import agh.ics.oop.model.entities.animal.stats.Statistics;
import agh.ics.oop.model.map.Borders.Borders;
import agh.ics.oop.model.map.utilities.MapDirection;
import agh.ics.oop.model.map.utilities.Vector2d;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements WorldElement {
    private final Genotype genotype;
    private final Statistics stats;
    private MapDirection orientation;
    private Vector2d position;
    private Integer energy;

    public Animal(Vector2d initialPosition,
                  Genotype genotype,
                  Statistics stats,
                  int startEnergy) {
        this.orientation = MapDirection.values()[ThreadLocalRandom.current().nextInt(MapDirection.values().length)];
        this.position = initialPosition;
        this.genotype = genotype;
        this.stats = stats;
        this.energy = startEnergy;
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

    public int getDayOfBirth() {
        return this.stats.getDayOfBirth();
    }

    public int getChildren() {
        return this.stats.getChildren();
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

    public void move(Borders<Vector2d> border) {
        Vector2d oldPosition = this.position;
        this.position = border.getPosition(this.position, this.orientation.toUnitVector());
        if(this.position.equals(oldPosition)) {
            this.orientation = this.orientation.next(4);
        }
        this.orientation = this.orientation.next(this.genotype.getMove());
    }

    public AnimalData getAnimalData() {
        return new AnimalData(
                this.stats.getDayOfBirth(),
                this.stats.getDayOfDeath(),
                this.stats.getChildren(),
                this.stats.getDescendants(),
                this.stats.getPlantsEaten(),
                Arrays.toString(this.genotype.getGenes()),
                this.genotype.getCurrentGene(),
                this.energy
        );
    }
}
