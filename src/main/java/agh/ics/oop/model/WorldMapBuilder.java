package agh.ics.oop.model;

public class WorldMapBuilder {
    public WorldMapBuilder(){}

    public WorldMap build(int width, int height, int initialPlantCount, int energyPerPlant, int dailyPlantGrowth,
     GrassPlanterEnum plantGrowthVariant,
     int energyForMating) {
        WorldMap map = new WorldMap(new Vector2d(width, height), energyPerPlant, energyForMating);
        if (plantGrowthVariant == GrassPlanterEnum.Jungle) {
            map.addPlanter(new Jungle(map.getMapSize()), initialPlantCount);
        } else {
            map.addPlanter(new Equator(map.getMapSize()), initialPlantCount);
        }
        map.addBorders(new Globe(map.getMapSize()));

        return map;
    }
}
