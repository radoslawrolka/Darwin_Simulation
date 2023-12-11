package agh.ics.oop.model;

import java.util.Random;

public class Genotype {
    protected final int[] genes;
    protected int current_gene;

    public Genotype(int[] parent1, int[] parent2, int energy1, int energy2) {
        this.genes = new int[parent1.length];
        this.current_gene = new Random().nextInt(this.genes.length);
        createGenotype(parent1, parent2, energy1, energy2);
    }

    public int[] getGenes() {
        return this.genes;
    }

    public int getMove() {
        this.current_gene = (this.current_gene + 1) % this.genes.length;
        return this.genes[this.current_gene];
    }

    private void createGenotype(int[] parent1, int[] parent2, int energy1, int energy2) {
        int ratio = (energy1 / (energy1+energy2))*this.genes.length;
        if (new Random().nextBoolean()) {
            System.arraycopy(parent1, 0, this.genes, 0, ratio);
            System.arraycopy(parent2, ratio, this.genes, ratio, this.genes.length - ratio);
        } else {
            System.arraycopy(parent2, 0, this.genes, 0, ratio);
            System.arraycopy(parent1, ratio, this.genes, ratio, this.genes.length - ratio);
        }
    }
}
