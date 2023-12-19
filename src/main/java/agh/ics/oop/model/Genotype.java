package agh.ics.oop.model;

import java.util.Random;

public class Genotype {
    protected final Integer[] genes;
    protected Integer current_gene;

    public Genotype(Integer length) {
        this.genes = new Integer[length];
        for (int i = 0; i < length; i++) {
            this.genes[i] = new Random().nextInt(8);
        }
        this.current_gene = new Random().nextInt(this.genes.length);
    }

    public Genotype(Integer[] parent1, Integer[] parent2, Integer energy1, Integer energy2) {
        this.genes = new Integer[parent1.length];
        this.current_gene = new Random().nextInt(this.genes.length);
        createGenotype(parent1, parent2, energy1, energy2);
    }

    public Integer[] getGenes() {
        return this.genes;
    }

    public Integer getMove() {
        this.current_gene = (this.current_gene + 1) % this.genes.length;
        return this.genes[this.current_gene];
    }

    private void createGenotype(Integer[] parent1, Integer[] parent2, int energy1, int energy2) {
        int ratio = (energy1 / (energy1+energy2))*this.genes.length;
        if (new Random().nextBoolean()) {
            System.arraycopy(parent1, 0, this.genes, 0, ratio);
            System.arraycopy(parent2, ratio, this.genes, ratio, this.genes.length - ratio);
        } else {
            System.arraycopy(parent2, 0, this.genes, 0, ratio);
            System.arraycopy(parent1, ratio, this.genes, ratio, this.genes.length - ratio);
        }
        int mutation = new Random().nextInt(0,this.genes.length+1);
        while (mutation < this.genes.length) {
            this.genes[mutation] = new Random().nextInt(8);
            mutation = new Random().nextInt(mutation, this.genes.length+1);
        }
    }
}
