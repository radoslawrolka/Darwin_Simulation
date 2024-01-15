package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.entities.animal.AnimalBuilder;
import agh.ics.oop.model.entities.animal.genotype.GenotypeEnum;
import agh.ics.oop.model.map.GrassPlanter.GrassPlanterEnum;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.model.map.WorldMapBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    WorldMap map = new WorldMapBuilder().build(10, 10, 10,1, GrassPlanterEnum.Equator,1);
    AnimalBuilder builder = new AnimalBuilder(32, GenotypeEnum.Normal, 100, 0,1,5);

    @Test
    public void spawnAnimalsTest(){
        Simulation sim = new Simulation(map, builder, 10, 10,10,4);
        assertEquals(sim.getAnimalsNumber(), 10);
    }

    @Test
    public void removeDeadTest(){
        Simulation sim = new Simulation(map, builder, 10, 10,10,4);
        sim.removeDead();
        assertEquals(sim.getAnimalsNumber(), 0);
    }
}
