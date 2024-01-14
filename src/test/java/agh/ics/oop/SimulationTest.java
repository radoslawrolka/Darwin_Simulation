package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    WorldMap map = new WorldMapBuilder().build(10, 10, 10,1,4, GrassPlanterEnum.Equator,1);
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
