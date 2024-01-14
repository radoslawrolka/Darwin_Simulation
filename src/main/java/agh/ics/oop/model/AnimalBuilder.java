package agh.ics.oop.model;

import java.util.function.BiFunction;
import java.util.function.Function;

public class AnimalBuilder {
    private final int genotypeLength;
    private final int START_ENERGY;
    private final int BREED_ENERGY;
    private final int MIN_MUTATION;
    private final int MAX_MUTATION;
    private int day = 0;
    private final Function<Integer, Genotype> genotypeConstructorSpawner;
    private final BiFunction<Integer[], Integer[], BiFunction<Integer, Integer, BiFunction<Integer, Integer, Genotype>>> genotypeConstructorBreeder;

    public AnimalBuilder(int genotypeLength, GenotypeEnum genotype, int BREED_ENERGY, int START_ENERGY, int MIN_MUTATION, int MAX_MUTATION) {
        this.genotypeLength = genotypeLength;
        this.BREED_ENERGY = BREED_ENERGY;
        this.START_ENERGY = START_ENERGY;
        this.MIN_MUTATION = MIN_MUTATION;
        this.MAX_MUTATION = MAX_MUTATION;
        if (genotype == GenotypeEnum.Normal) {
            genotypeConstructorSpawner = Genotype::new;
            genotypeConstructorBreeder = (genes1, genes2) -> (energy1, energy2) -> (minmut, maxmut) -> new Genotype(genes1, genes2, energy1, energy2, minmut, maxmut);
        } else {
            genotypeConstructorSpawner = CrazyGenotype::new;
            genotypeConstructorBreeder = (genes1, genes2) -> (energy1, energy2) -> (minmut, maxmut) -> new CrazyGenotype(genes1, genes2, energy1, energy2, minmut, maxmut);
        }
    }

    public void incrementDay () {
        this.day++;
    }

    public Animal spawn(Vector2d initialPosition) {
        return new Animal(initialPosition,
                          this.genotypeConstructorSpawner.apply(this.genotypeLength),
                          new Statistics(this.day),
                          this.START_ENERGY);
    }

    public Animal build(Animal parent1, Animal parent2) {
        return new Animal(parent1.getPosition(),
                          this.genotypeConstructorBreeder.apply(parent1.getGenotype().getGenes(),
                                                                parent2.getGenotype().getGenes())
                                                         .apply(parent1.getEnergy(),
                                                                parent2.getEnergy())
                                                         .apply(this.MIN_MUTATION,
                                                                this.MAX_MUTATION),
                          new Statistics(parent1.getStats(), parent2.getStats(), this.day),
                          this.BREED_ENERGY);
    }
}
