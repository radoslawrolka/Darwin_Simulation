package agh.ics.oop.model;

public record AnimalData(
        int dayOfBirth,
        int dayOfDeath,
        int children,
        int descendants,
        int plantsEaten,
        String genotype,
        int activeGeneIndex,
        int energy) {
}
