package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

public class StatisticsTest {
    private final Statistics statistics1 = new Statistics(3);
    private final Statistics statistics2 = new Statistics(9);
    private final Statistics statistics3 = new Statistics(statistics1, statistics2, 15);
    private final Statistics statistics4 = new Statistics(statistics1, statistics2, 21);
    private final Statistics statistics5 = new Statistics(statistics3, statistics4, 27);

    @Test
    public void testAddDay() {
        statistics1.addDay();
        assert statistics1.getDaysAlive() == 1;
        statistics1.addDay();
        assert statistics1.getDaysAlive() == 2;
    }

    @Test
    public void testAddPlant() {
        statistics1.addPlant();
        assert statistics1.getPlantsEaten() == 1;
        statistics1.addPlant();
        assert statistics1.getPlantsEaten() == 2;
    }

    @Test
    public void testAddChild() {
        assert statistics1.getChildren() == 2;
        assert statistics2.getChildren() == 2;
        assert statistics3.getChildren() == 1;
        assert statistics4.getChildren() == 1;
        assert statistics5.getChildren() == 0;
    }

    @Test
    public void testAddDescendant() {
        assert statistics1.getDescendants() == 3;
        assert statistics2.getDescendants() == 3;
        assert statistics3.getDescendants() == 1;
        assert statistics4.getDescendants() == 1;
        assert statistics5.getDescendants() == 0;
    }

    @Test
    public void testSetDayOfDeath() {
        statistics1.setDayOfDeath(5);
        assert statistics1.getDayOfDeath() == 5;
        statistics1.setDayOfDeath(10);
        assert statistics1.getDayOfDeath() == 10;
    }

    @Test
    public void testGetDayOfBirth() {
        assert statistics1.getDayOfBirth() == 3;
        assert statistics2.getDayOfBirth() == 9;
        assert statistics3.getDayOfBirth() == 15;
        assert statistics4.getDayOfBirth() == 21;
        assert statistics5.getDayOfBirth() == 27;
    }
}
