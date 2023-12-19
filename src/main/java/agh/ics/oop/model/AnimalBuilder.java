package agh.ics.oop.model;

import java.util.function.Function;

public class AnimalBuilder {
    private int genotypeLength;
    private int energy;
    private int day = 0;
    private Function<Integer, Genotype> genotypeConstructorSpawner;
    private Function<Integer[], Function<Integer[], Function<Integer, Function<Integer, Genotype>>>> genotypeConstructorBreeder;

    public AnimalBuilder(int genotypeLength,
                         GenotypeEnum genotype,
                         int energy) {
        this.genotypeLength = genotypeLength;
        this.energy = energy;
        if (genotype == GenotypeEnum.NORMAL) {
            genotypeConstructorSpawner = length -> new Genotype(length);
            genotypeConstructorBreeder = genes1 -> genes2 -> energy1 -> energy2 -> new Genotype(genes1, genes2, energy1, energy2);
        } else {
            genotypeConstructorSpawner = length -> new CrazyGenotype(length);
            genotypeConstructorBreeder = genes1 -> genes2 -> energy1 -> energy2 -> new CrazyGenotype(genes1, genes2, energy1, energy2);
        }
        System.setProperty("energy", Integer.toString(energy));
    }

    public void incrementDay () {
        this.day++;
    }

    public Animal spawn(Vector2d initialPosition) {
        return new Animal(initialPosition,
                          this.genotypeConstructorSpawner.apply(this.genotypeLength),
                          new Statistics(this.day));
    }

    public Animal build(Animal parent1, Animal parent2) {
        return new Animal(parent1.getPosition(),
                          this.genotypeConstructorBreeder.apply(parent1.getGenotype().getGenes())
                                                         .apply(parent2.getGenotype().getGenes())
                                                         .apply(parent1.getEnergy())
                                                         .apply(parent2.getEnergy()),
                          new Statistics(parent1.getStats(), parent2.getStats(), this.day));
    }
}
