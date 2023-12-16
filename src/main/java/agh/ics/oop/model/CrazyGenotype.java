package agh.ics.oop.model;

import java.util.Random;

public class CrazyGenotype extends Genotype{
    public CrazyGenotype(Integer length) {
        super(length);
    }

    public CrazyGenotype(Integer[] parent1, Integer[] parent2, Integer energy1, Integer energy2) {
        super(parent1, parent2, energy1, energy2);
    }

    @Override
    public Integer getMove() {
        if (new Random().nextInt(5) != 4) {
            return super.getMove();
        }
        else {
            this.current_gene = new Random().nextInt(this.genes.length);
            return this.genes[this.current_gene];
        }
    }
}
