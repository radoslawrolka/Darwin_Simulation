package agh.ics.oop.model;

import java.util.Comparator;

public class AnimalComparator implements java.util.Comparator<Animal>{
    /*
    pierwszeństwo mają organizmy o największej energii,
    jeżeli to nie pozwala rozstrzygnąć, to pierwszeństwo mają organizmy najstarsze,
    jeżeli to nie pozwala rozstrzygnąć, to pierwszeństwo mają organizmy o największej liczbie dzieci,
    jeżeli to nie pozwala rozstrzygnąć, to wśród remisujących organizmów wybieramy losowo.
    */
    @Override
    public int compare(Animal a1, Animal a2) {
        return Comparator.comparingInt(Animal::getEnergy)
                .thenComparing(Animal::getDayOfBirth, Comparator.reverseOrder())
                .thenComparingInt(Animal::getChildren)
                .thenComparing(Animal::hashCode)
                .compare(a1, a2);
    }
}
