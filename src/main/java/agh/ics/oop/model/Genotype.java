package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genotype {
    protected final Integer[] genes;
    protected Integer current_gene;

    public Genotype(Integer length) {
        this.genes = new Integer[length];
        for (int i = 0; i < length; i++) {
            this.genes[i] = ThreadLocalRandom.current().nextInt(8);
        }
        this.current_gene = ThreadLocalRandom.current().nextInt(this.genes.length);
    }

    public Genotype(Integer[] parent1, Integer[] parent2, Integer energy1, Integer energy2, Integer min_mutation, Integer max_mutation) {
        this.genes = new Integer[parent1.length];
        this.current_gene = ThreadLocalRandom.current().nextInt(this.genes.length);
        createGenotype(parent1, parent2, energy1, energy2, min_mutation, max_mutation);
    }

    public Integer[] getGenes() {
        return this.genes;
    }

    public Integer getMove() {
        this.current_gene = (this.current_gene + 1) % this.genes.length;
        return this.genes[this.current_gene];
    }

    private void createGenotype(Integer[] parent1, Integer[] parent2, int energy1, int energy2, int min_mutation, int max_mutation) {
        int ratio = (energy1 / (energy1+energy2))*this.genes.length;
        if (ThreadLocalRandom.current().nextBoolean()) {
            System.arraycopy(parent1, 0, this.genes, 0, ratio);
            System.arraycopy(parent2, ratio, this.genes, ratio, this.genes.length - ratio);
        } else {
            System.arraycopy(parent2, 0, this.genes, 0, ratio);
            System.arraycopy(parent1, ratio, this.genes, ratio, this.genes.length - ratio);
        }
        int mutation = ThreadLocalRandom.current().nextInt(min_mutation, max_mutation+1);
        ArrayList<Integer> randtab = IntStream.range(0, this.genes.length).boxed().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < mutation; i++) {
            Collections.shuffle(randtab);
            int idx = randtab.get(0);
            this.genes[idx] = ThreadLocalRandom.current().nextInt(8);
        }
    }
}
