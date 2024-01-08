package agh.ics.oop.model;

public class WorldMapBuilder {

    private WorldMap map;

    public WorldMapBuilder(int width, int height, int grassEnergy, int breedEnergy){
        map = new WorldMap(new Vector2d(width,height), grassEnergy, breedEnergy);
    }

    public void buildJungle(int grassStartNumber){
        map.addPlanter(new Jungle(map.getMapSize()), grassStartNumber);
    }

    public void buildEquator(int grassStartNumber, int lowerY, int upperY){
        map.addPlanter(new Equator(map.getMapSize(),lowerY, upperY), grassStartNumber);
    }

    public void buildGlobe(){
        map.addBorders(new Globe(map.getMapSize()));
    }

    public WorldMap build(int grassPlanterType, int grassStartNumber, int lowerY, int upperY) {
        if (grassPlanterType == 1) {
            buildJungle(grassStartNumber);
        } else {
            buildEquator(grassStartNumber, lowerY, upperY);
        }
        buildGlobe();
        return map;
    }
}
