package agh.ics.oop.model;

public record ConfigData(
        int mapHeight,
        int mapWidth,
        int initialPlantCount,
        int energyPerPlant,
        int dailyPlantGrowth,
        GrassPlanterEnum plantGrowthVariant,
        int initialAnimalCount,
        int initialAnimalEnergy,
        int energyForMating,
        int breededAnimalEnergy,
        int energyLossPerDay,
        int minMutations,
        int maxMutations,
        int genomeLength,
        GenotypeEnum animalBehaviourVariant,
        String saveLogs) {
}
