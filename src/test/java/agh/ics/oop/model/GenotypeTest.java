package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GenotypeTest {
    private final Genotype genotype1 = new Genotype(5);
    private final Integer[] genes1 = new Integer[]{0, 1, 2, 3};
    private final Integer[] genes2 = new Integer[]{6, 7, 8, 9};
    private final Genotype genotype2 = new Genotype(genes1, genes2, 10, 10);

    @Test
    public void testGetGenes() {
        Integer[] genes = genotype1.getGenes();
        assertEquals(5, genes.length);
        for (Integer gene : genes) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }

    @Test
    public void testGetMove() {
        Integer[] genes = genotype1.getGenes();
        for (Integer gene : genes) {
            assertEquals(gene, genotype1.getMove());
        }
    }

    @Test
    public void testCreateGenotype() {
        Integer[] genes = genotype2.getGenes();
        assertEquals(5, genes.length);
        for (Integer gene : genes) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }

}
