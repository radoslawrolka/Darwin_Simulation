package agh.ics.oop.model;

import java.util.Random;

public class Genotype {
    private final int[] genes;
    private int current_gene = 0;

    public Genotype(int[] parent1, int[] parent2, int energy1, int energy2) {
        this.genes = new int[parent1.length];
        createGenotype(parent1, parent2, energy1, energy2);
        this.current_gene = new Random().nextInt(this.genes.length);
    }

    public int getGene() {
        this.current_gene = (this.current_gene + 1) % this.genes.length;
        return this.genes[this.current_gene];
    }

    public void createGenotype(int[] parent1, int[] parent2, int energy1, int energy2) {
    }

    public int[] getGenes() {
        return this.genes;
    }
}
