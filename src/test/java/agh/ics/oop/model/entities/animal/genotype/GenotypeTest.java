package agh.ics.oop.model.entities.animal.genotype;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GenotypeTest {
    private final Genotype genotype1 = new Genotype(5);
    private final Integer[] genes1 = new Integer[]{0, 1, 2, 3};
    private final Integer[] genes2 = new Integer[]{4, 5, 6, 7};
    private final Genotype genotype2 = new Genotype(genes1, genes2, 20,30, 1,5);

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
        List<Integer> result = new LinkedList<>();
        Collections.addAll(result, genes);
        List<Integer> doubled = new LinkedList<>(result);
        doubled.addAll(result);
        assertTrue(doubled.containsAll(result));
    }

    @Test
    public void testCreateGenotype() {
        Integer[] genes = genotype2.getGenes();
        assertEquals(4, genes.length);
        for (Integer gene : genes) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }

}
