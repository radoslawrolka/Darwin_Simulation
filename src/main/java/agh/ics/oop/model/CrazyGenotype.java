package agh.ics.oop.model;

import java.util.concurrent.ThreadLocalRandom;

public class CrazyGenotype extends Genotype{
    public CrazyGenotype(Integer length) {
        super(length);
    }

    public CrazyGenotype(Integer[] parent1, Integer[] parent2, Integer energy1, Integer energy2, Integer min_mutation, Integer max_mutation) {
        super(parent1, parent2, energy1, energy2, min_mutation, max_mutation);
    }

    @Override
    public Integer getMove() {
        if (ThreadLocalRandom.current().nextInt(5) != 4) {
            return super.getMove();
        }
        else {
            this.current_gene = ThreadLocalRandom.current().nextInt(this.genes.length);
            return this.genes[this.current_gene];
        }
    }
}
