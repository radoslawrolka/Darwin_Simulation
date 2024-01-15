package agh.ics.oop.gui.utilities;

import agh.ics.oop.model.entities.animal.genotype.GenotypeEnum;
import agh.ics.oop.model.map.GrassPlanter.GrassPlanterEnum;

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
        String saveLogs,
        int moveDelay) {
}
