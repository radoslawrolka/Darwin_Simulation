package agh.ics.oop.model;

public class Statistics {
    private int children = 0;
    private int descendants = 0;
    private int daysAlive = 0;
    private int plantsEaten = 0;
    private final int dayOfBirth;
    private int dayOfDeath = -1;
    private Statistics parent1 = null;
    private Statistics parent2 = null;

    public Statistics(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Statistics(Statistics parent1, Statistics parent2, int dayOfBirth) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.dayOfBirth = dayOfBirth;
    }

    public void addDay() {
        this.daysAlive++;
    }

    public void addPlant() {
        this.plantsEaten++;
    }

    public void addChild() {
        this.children++;
        this.descendants++;
        if (this.parent1 != null) {
            this.parent1.addDescendant();
        }
        if (this.parent2 != null) {
            this.parent2.addDescendant();
        }
    }

    public void addDescendant() {
        this.descendants++;
        if (this.parent1 != null) {
            this.parent1.addDescendant();
        }
        if (this.parent2 != null) {
            this.parent2.addDescendant();
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
    public int getDaysAlive() {
        return this.daysAlive;
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