package agh.ics.oop.map;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.*;

import java.util.*;

public class World {
    Random random = new Random();
    private final int width;
    private final int height;
    private final IMapBorder mapBorder;
    private final IMapGrounds mapGrounds;
    private int day = 0;
    private SimulationEngine engine;

    private final int PlantsEnergy;
    private final int SpawnPlantsDay;

    private final int AnimalStartEnergy;
    private final int AnimalMaxEnergy;
    private final int AnimalReproductionEnergy;
    private final int MinimumMutations;
    private final int MaximumMutations;
    private final int GenesLength;
    private final MutationStyle mutationStyle;
    private final MovingStyle MovingStyle;
    private final int startingPlants;
    private final int startingAnimals;

    private Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private Map<Vector2d, Plant> plants = new HashMap<>();
    private List<Vector2d> freePositions = new ArrayList<>();
    private Map<Vector2d, Integer> cementary = new HashMap<>();
    private Map<String, Integer> genomes = new HashMap<>();

    private int deadNum = 0;
    private int deadSumLife = 0;

    public World(int width, int height, IMapBorder mapBorder, IMapGrounds mapGrounds, int plantsEnergy, int startingPlants, int spawnPlantsDay, int animalStartEnergy, int animalReproductionEnergy, int startingAnimals, int minimumMutations, int maximumMutations, int genesLength, int animalMaxEnergy, MovingStyle movingStyle, MutationStyle mutationStyle) {
        this.width = width;
        this.height = height;
        this.mapBorder = mapBorder;
        this.mapGrounds = mapGrounds;
        this.PlantsEnergy = plantsEnergy;
        this.SpawnPlantsDay = spawnPlantsDay;
        this.AnimalStartEnergy = animalStartEnergy;
        this.AnimalReproductionEnergy = animalReproductionEnergy;
        this.MinimumMutations = minimumMutations;
        this.MaximumMutations = maximumMutations;
        this.GenesLength = genesLength;
        this.AnimalMaxEnergy = animalMaxEnergy;
        this.MovingStyle = movingStyle;
        this.mutationStyle = mutationStyle;
        this.startingAnimals = startingAnimals;
        this.startingPlants = startingPlants;

        for (int x=1; x<=width; x++) {
            for (int y=1; y<=height; y++) {
                Vector2d position = new Vector2d(x,y);
                freePositions.add(position);
            }
        }
        if (mapGrounds instanceof ToxicCorpses) {
            ((ToxicCorpses) mapGrounds).addMap(this);
        }
    }

    public void addEngine(SimulationEngine engine) {
        this.engine = engine;
        for (int i=0; i<startingPlants; i++) {spawnPlant();}
        for (int i=0; i<startingAnimals; i++) {spawnAnimal();}
    }

    public void spawnPlant() {
        Vector2d position = freePositions.get(random.nextInt(freePositions.size()));
        if (mapGrounds.willBePlanted(position)) {
            Plant plant = new Plant(position);
            plants.put(position, plant);
            freePositions.remove(position);
            }
        else {
            spawnPlant();
        }
    }

    public void spawnAnimal() {
        int x = random.nextInt(width) + 1;
        int y = random.nextInt(height) + 1;
        Vector2d position = new Vector2d(x,y);
        Animal animal = new Animal(this, position, AnimalStartEnergy, AnimalMaxEnergy, 0, new Genotype().spawnGenes(GenesLength), MovingStyle);
        engine.addAnimal(animal);
        if (animals.containsKey(position)) {
            animals.get(position).add(animal);
        }
        else {
            List<Animal> list = new ArrayList<>();
            list.add(animal);
            animals.put(position, list);
        }
        if (genomes.containsKey(Arrays.toString(animal.getGenes()))) {
            genomes.put(Arrays.toString(animal.getGenes()), genomes.get(Arrays.toString(animal.getGenes())) + 1);
        }
        else {
            genomes.put(Arrays.toString(animal.getGenes()), 1);
        }
    }

    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        deadNum++;
        deadSumLife += animal.getAge();
        animals.get(position).remove(animal);
        genomes.put(Arrays.toString(animal.getGenes()), genomes.get(Arrays.toString(animal.getGenes())) - 1);
        if (animals.get(position).size() == 0) {
            animals.remove(position);
        }
        if (cementary.containsKey(position)) {
            cementary.put(position, cementary.get(position) + 1);
        }
        else {
            cementary.put(position, 1);
        }
    }

    public void changePosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animals.get(oldPosition).remove(animal);
        if (animals.get(oldPosition).size() == 0) {
            animals.remove(oldPosition);
        }
        if (animals.containsKey(newPosition)) {
            animals.get(newPosition).add(animal);
        }
        else {
            List<Animal> list = new ArrayList<>();
            list.add(animal);
            animals.put(newPosition, list);
        }
    }

    public void eatPlants() {
        Map<Vector2d, Plant> newList = new HashMap<>();
        for (Vector2d position : plants.keySet()) {
            if (animals.containsKey(position)) {
                List<Animal> list = animals.get(position);
                Animal topG = list.get(0);
                for (Animal animal : list) {
                    topG = topG.compare(animal);
                }
                topG.eat(PlantsEnergy);
                freePositions.add(position);
            }
            else {
                newList.put(position, plants.get(position));
            }
        }
        plants = newList;
    }

    public void reproduce() {
        for (Vector2d position : animals.keySet()) {
            List<Animal> list = animals.get(position);
            if (list.size() > 1) {
                Animal topG = list.get(0);
                Animal topG2 = list.get(1);
                for (Animal animal : list) {
                    if (animal == topG || animal == topG2) {
                        continue;
                    }
                    Animal buff = topG;
                    topG = topG.compare(animal);
                    if (buff != topG) {
                        topG2 = buff;
                    }
                    else {topG2 = topG2.compare(animal);}
                }
                if (topG.getEnergy() > AnimalReproductionEnergy && topG2.getEnergy() > AnimalReproductionEnergy) {
                    int ratio = (int) (((double)topG.getEnergy() / (double)(topG.getEnergy() + topG2.getEnergy())) * AnimalStartEnergy);
                    topG.removeEnergy(ratio);
                    topG2.removeEnergy(AnimalStartEnergy - ratio);
                    topG.addChild();
                    topG2.addChild();
                    Animal kid = new Animal(this, position, AnimalStartEnergy, AnimalMaxEnergy, day, new Genotype().createGenotype(topG, topG2, mutationStyle, MinimumMutations, MaximumMutations), MovingStyle);
                    if (animals.containsKey(position)) {
                        animals.get(position).add(kid);
                    }
                    else {
                        List<Animal> list2 = new ArrayList<>();
                        list2.add(kid);
                        animals.put(position, list2);
                    }
                    if (genomes.containsKey(Arrays.toString(kid.getGenes()))) {
                        genomes.put(Arrays.toString(kid.getGenes()), genomes.get(Arrays.toString(kid.getGenes())) + 1);
                    }
                    else {
                        genomes.put(Arrays.toString(kid.getGenes()), 1);
                    }
                    engine.addAnimal(kid);
                }
            }
        }
    }

    public void growPlants() {
        for (int i=0; i<SpawnPlantsDay; i++) {
            spawnPlant();
        }
    }

    public void verify(Vector2d position, Animal animal) {
        if (!mapBorder.canMoveTo(position)) {
            mapBorder.correctPosition(animal);
        }
    }

    public void addDay() {
        day++;
    }

    public String isAt(Vector2d position) {
        if (animals.containsKey(position)) {
            return String.valueOf(animals.get(position).size());
        }
        else if (plants.containsKey(position)) {
            return "plant";
        }
        else {
            return " ";
        }
    }

    public String groundType(Vector2d position) {
        return mapGrounds.groundType(position);
    }

    public boolean isDeadHere(Vector2d position) {
        return cementary.containsKey(position);
    }

    public int getPlantsNum() {
        return plants.keySet().size();
    }
    public int getAvgDeath() {
        return (int) ((double)deadSumLife/(double)deadNum);
    }

    public String getTopGenom() {
        String top = "None";
        int num=-1;
        for (String gen :genomes.keySet()) {
            if (genomes.get(gen) > num) {
                top = gen;
                num = genomes.get(gen);
            }
        }
        return top;
    }

    public void draw() {
        System.out.println("x|1|2|3|4|5|6|7|8|9|0|");

        for(int y=10; y>0; y--) {
            for(int x=0; x<=10; x++) {
                if (x==0) {
                    if (y != 10) {
                        System.out.print(y + "|");
                    } else {
                        System.out.print("0|");
                    }
                    continue;
                }
                Vector2d position = new Vector2d(x,y);
                if (animals.containsKey(position)) {
                    System.out.print(animals.get(position).size() + "|");
                }
                else if (plants.containsKey(position)) {
                    System.out.print("*|");
                }
                else {
                    System.out.print(" |");
                }
            }
            System.out.println("|");

        }


    }

}
