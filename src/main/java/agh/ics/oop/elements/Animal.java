package agh.ics.oop.elements;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;
import agh.ics.oop.map.World;

import java.util.Random;

public class Animal implements IElement {
    private static final Random random = new Random();
    private Vector2d position;
    private MapDirection orientation;

    private final MovingStyle movingStyle;
    private final World map;

    private int energy;
    private final int maxEnergy;
    private int age;
    private final int birthDay;
    private int deathDay;
    private final int[] genes;
    private int genesIndex;
    private int counterChildren;
    private int counterPlantsEaten;

    public Animal(World map, Vector2d initialPosition, int initialEnergy, int maxEnergy, int day, int[] genes, MovingStyle movingStyle) {
        this.position = initialPosition;
        this.orientation = MapDirection.values()[random.nextInt(8)];
        this.movingStyle = movingStyle;
        this.map = map;
        this.energy = initialEnergy;
        this.maxEnergy = maxEnergy;
        this.age = 0;
        this.birthDay = day;
        this.deathDay = -1;
        this.genes = genes;
        this.genesIndex = random.nextInt(genes.length);
        this.counterChildren = 0;
        this.counterPlantsEaten = 0;
    }

    public Vector2d getPosition() {return position;}
    public MapDirection getOrientation() {return orientation;}
    public int getEnergy() {return energy;}
    public int getAge() {return age;}
    public int getBirthDay() {return birthDay;}
    public int getDeathDay() {return deathDay;}
    public int[] getGenes() {return genes;}
    public int getGenesIndex() {return genesIndex;}
    public int getCounterChildren() {return counterChildren;}
    public int getCounterPlantsEaten() {return counterPlantsEaten;}

    public void setPosition(Vector2d position) {
        this.position = position;
    }
    public void removeEnergy(int energy) {this.energy -= energy;}
    public void addAge() {this.age++;}
    public void setDeathDay(int day) {this.deathDay = day;}
    public void addChild() {this.counterChildren++;}

    public void move() {
        if (this.movingStyle == MovingStyle.FULLY_PREDESTINED) {
            this.genesIndex = (this.genesIndex + 1) % this.genes.length;
        }
        else if (this.movingStyle == MovingStyle.BIT_OF_RANDOMNESS){
            if (random.nextDouble() > 0.8) {this.genesIndex = random.nextInt(this.genes.length);}
            else {this.genesIndex = (this.genesIndex + 1) % this.genes.length;}
        }

        this.orientation = this.orientation.rotate(this.genes[this.genesIndex]);
        Vector2d oldPosition = this.position;
        this.position = this.position.add(this.orientation.unitVector());
        map.verify(this.position, this);
        map.changePosition(oldPosition, this.position, this);
    }

    public void eat(int amount) {
        this.energy += amount;
        if (this.energy > this.maxEnergy) {this.energy = this.maxEnergy;}
        this.counterPlantsEaten++;

    }

    public Animal compare(Animal other) {
        if (this.energy > other.energy) {return this;}
        else if (this.energy < other.energy) {return other;}
        else {
            if (this.age > other.age) {return this;}
            else if (this.age < other.age) {return other;}
            else {
                if (this.counterChildren > other.counterChildren) {return this;}
                else if (this.counterChildren < other.counterChildren) {return other;}
                else {
                    if (random.nextBoolean()) {return this;}
                    else {return other;}
                }
            }
        }
    }









    //-----------------------
    @Override
    public String getImagePath () {
        return null;
    }
}
