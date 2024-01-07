package agh.ics.oop.model;

public class WorldMapBuilder {

    private WorldMap map;

    public WorldMapBuilder(int width, int height, int grassStartNumber){
        map = new WorldMap(new Vector2d(width,height), grassStartNumber);
    }

    public void buildJungle(){
        map.addPlanter(new Jungle(map.getMapSize()));
    }

    public void buildEquator(int lowerY, int upperY){
        map.addPlanter(new Equator(map.getMapSize(),lowerY, upperY));
    }

    public void buildGlobe(){
        map.addBorders(new Globe(map.getMapSize()));
    }

    public WorldMap build(int grassPlanterType,int lowerY, int upperY) {
        if (grassPlanterType == 1) {
            buildJungle();
        } else {
            buildEquator(lowerY, upperY);
        }
        buildGlobe();
        return map;
    }
}
