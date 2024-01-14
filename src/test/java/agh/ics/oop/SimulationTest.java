package agh.ics.oop;
/*
import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    public final List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(0, 0));
    public final int width = 4;
    public final int height = 4;
    public final WorldMap map = new RectangularMap(width, height);

    @Test
    public void OrientTest() {
        String[] args = new String[]{"r", "l", "r", "l", "r", "l"};
        List<MoveDirection> moves = OptionParser.parse(args);
        Simulation simulation = new Simulation(moves, positions, map);

        simulation.run();

        assertEquals(simulation.getAnimals().get(0).getOrientation(), MapDirection.WEST);
        assertEquals(simulation.getAnimals().get(1).getOrientation(), MapDirection.EAST);
    }

    @Test
    public void MoveTest() {
        String[] args = new String[]{"b", "f", "b", "f"};
        List<MoveDirection> moves = OptionParser.parse(args);
        Simulation simulation = new Simulation(moves, positions, map);

        simulation.run();

        assertEquals(simulation.getAnimals().get(0).getPosition(), new Vector2d(2, 0));
        assertEquals(simulation.getAnimals().get(1).getPosition(), new Vector2d(0, 2));
    }

    @Test
    public void BorderTest() {
        String[] args = new String[]{"f", "l", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> moves = OptionParser.parse(args);
        Simulation simulation = new Simulation(moves, positions, map);

        simulation.run();

        assertEquals(simulation.getAnimals().get(0).getPosition(), new Vector2d(2, 3));
        assertEquals(simulation.getAnimals().get(1).getPosition(), new Vector2d(0, 0));
    }

    @Test
    public void ParseTest() {
        String[] args = new String[]{"f", "l", "r", "b", "f"};
        List<MoveDirection> moves = OptionParser.parse(args);
        Simulation simulation = new Simulation(moves, positions, map);

        assertEquals(simulation.getMoves(), List.of(MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.FORWARD));
    }
}
*/