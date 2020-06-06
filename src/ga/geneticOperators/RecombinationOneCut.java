package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.Arrays;

public class RecombinationOneCut<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    private int[] child1, child2;
    private int cut;

    public RecombinationOneCut(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        child1 = new int[ind1.getNumGenes()];
        child2 = new int[ind2.getNumGenes()];
        do {
            cut = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        } while (cut == 0 || cut > child1.length/2 || cut==child1.length-1);
        System.out.println(child1.length + " - " + cut);
        for (int i = cut; i < child1.length; i++) {
            child1[i] = ind1.getGene(i);
            child2[i] = ind2.getGene(i);
        }
        for (int i = 0; i < cut; i++) {
            if (!hasGene(ind1, ind2.getGene(i))) {
                child1[i] = ind2.getGene(i);
            }
            if (!hasGene(ind2, ind1.getGene(i))) {
                child2[i] = ind1.getGene(i);
            }
        }
        for (int i = 0; i < ind1.getNumGenes() - 1; i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }

    public boolean hasGene(I ind, int gene){
        return ind.getIndexof(gene) >= 0
                ? true
                : false;
    }

    @Override
    public String toString(){
        return "OneCut";
    }
}