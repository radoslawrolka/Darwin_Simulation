package agh.ics.oop.model;

import java.util.HashSet;

public class Statistics {
    private int children = 0;
    private int descendants = 0;
    private int plantsEaten = 0;
    private int dayOfDeath = -1;
    private final int dayOfBirth;
    private int lastDescendant = -1;
    private Statistics parent1 = null;
    private Statistics parent2 = null;

    public Statistics(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Statistics(Statistics parent1, Statistics parent2, int dayOfBirth) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.dayOfBirth = dayOfBirth;
        this.parent1.addChild(this.hashCode());
        this.parent2.addChild(this.hashCode());
    }

    public void addPlant() {
        this.plantsEaten++;
    }

    private void addChild(int hashChild) {
        this.children++;
        addDescendant(hashChild);
    }

    private void addDescendant(int hashDescendant) {
        if (this.lastDescendant == hashDescendant) {
            return;
        }
        this.lastDescendant = hashDescendant;
        this.descendants++;
        if (this.parent1 != null) {
            this.parent1.addDescendant(hashDescendant);
        }
        if (this.parent2 != null) {
            this.parent2.addDescendant(hashDescendant);
        }
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getChildren() {
        return this.children;
    }
    public int getDescendants() {
        return this.descendants;
    }
    public int getPlantsEaten() {
        return this.plantsEaten;
    }
    public int getDayOfBirth() {
        return this.dayOfBirth;
    }
    public int getDayOfDeath() {
        return this.dayOfDeath;
    }
}