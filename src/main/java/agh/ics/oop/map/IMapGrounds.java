package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

public interface IMapGrounds {
    boolean isPreferable(Vector2d position);
    boolean willBePlanted(Vector2d position);
    String groundType(Vector2d position);
}
