package agh.ics.oop.model;

import java.util.function.Function;

public class AnimalBuilder {
    private int genotypeLength;
    private int energy;
    private Function<Integer, Genotype> genotypeConstructorSpawner;
    private Function<Integer[], Function<Integer[], Function<Integer, Function<Integer, Genotype>>>> genotypeConstructorBreeder;

    public AnimalBuilder() {}

    public AnimalBuilder genotypeLength(int genotypeLength) {
        this.genotypeLength = genotypeLength;
        return this;
    }

    public AnimalBuilder energy(int energy) {
        this.energy = energy;
        return this;
    }

    public AnimalBuilder genotype(GenotypeEnum genotype) {
        if (genotype == GenotypeEnum.NORMAL) {
            genotypeConstructorSpawner = length -> new Genotype(length);
            genotypeConstructorBreeder = genes1 -> genes2 -> energy1 -> energy2 -> new Genotype(genes1, genes2, energy1, energy2);
        } else {
            genotypeConstructorSpawner = length -> new CrazyGenotype(length);
            genotypeConstructorBreeder = genes1 -> genes2 -> energy1 -> energy2 -> new CrazyGenotype(genes1, genes2, energy1, energy2);
        }
        return this;
    }

    public Animal spawn(Vector2d initialPosition) {
        return new Animal(initialPosition,
                          this.energy,
                          this.genotypeConstructorSpawner.apply(this.genotypeLength));
    }

    public Animal build(Animal parent1, Animal parent2) {
        return new Animal(parent1.getPosition(),
                          this.genotypeConstructorBreeder.apply(parent1.getGenotype().getGenes())
                                                         .apply(parent2.getGenotype().getGenes())
                                                         .apply(parent1.getEnergy())
                                                         .apply(parent2.getEnergy()),
                          this.energy);
    }
}
