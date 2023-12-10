package agh.ics.oop.model;

public class Genotype {

    private String genotype;

    private int current_gene = 0;

    public Genotype(String genotype_p1, String genotype_p2){
        this.genotype = genotype_p1 + genotype_p2;
    }

    public void nextGene(){
        current_gene++;
        if(current_gene == genotype.length()){
            current_gene = 0;
        }
    }
}
