package agh.ics.oop.model;

public class CrazyGenotype extends Genotype{
    public CrazyGenotype(int[] parent1, int[] parent2, int energy1, int energy2) {
        super(parent1, parent2, energy1, energy2);
    }

    @Override
    public int getGene() {
        return 1;
    }
}
