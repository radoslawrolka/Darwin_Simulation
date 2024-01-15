package agh.ics.oop.model.map.utilities;

import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.model.map.utilities.Boundary;
import agh.ics.oop.model.map.utilities.MapChangeListener;
import agh.ics.oop.model.util.MapVisualizer;

public class ConsoleMapDisplay implements MapChangeListener {
    private int update = 1;

    @Override
    public void mapChanged(WorldMap worldmap) {
        synchronized (System.out) {
            System.out.println("To był: "+update+" update mapy id: "+worldmap.getId());
            Boundary bounds = worldmap.getCurrentBounds();
            System.out.println(new MapVisualizer(worldmap).draw(bounds.lowerLeft(), bounds.upperRight()));
            update++;
        }
    }
}
