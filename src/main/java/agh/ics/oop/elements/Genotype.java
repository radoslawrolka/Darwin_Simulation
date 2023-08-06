package agh.ics.oop.elements;

import java.util.ArrayList;
import java.util.Random;

public class Genotype {
    public int[] createGenotype(Animal parent1, Animal parent2, MutationStyle mutationStyle, int numberOfMutations) {
        int[] genes1 = parent1.getGenes();
        int[] genes2 = parent2.getGenes();
        int[] result = new int[genes1.length];
        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();

        Random random = new Random();
        int ratio = (energy1 / (energy1 + energy2))*genes2.length;

        //True == left, False == right
        if (random.nextBoolean()) {
            System.arraycopy(genes1, 0, result, 0, ratio);
            System.arraycopy(genes2, ratio, result, ratio, result.length-ratio);
        }
        else {
            System.arraycopy(genes2, 0, result, 0, ratio);
            System.arraycopy(genes1, ratio, result, ratio, result.length-ratio);
        }

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {indexes.add(i);}

        if (mutationStyle == MutationStyle.FULLY_RANDOM) {
            for (int counter = 0; counter < numberOfMutations; counter++) {
                int i = indexes.remove(random.nextInt(indexes.size()));
                result[i] = random.nextInt(8);
            }
        }
        else if (mutationStyle == MutationStyle.LITTLE_CORRECTION) {
            for (int counter = 0; counter < numberOfMutations; counter++) {
                int i = indexes.remove(random.nextInt(indexes.size()));
                if (random.nextBoolean()) {result[i] = (result[i] + 1) % 8;}
                else {result[i] = (result[i] + 7) % 8;}
            }
        }

        return result;
    }



}
