package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        } while (cut1 == cut2);
        int aux = ind.getGene(cut1);
        ind.setGene(cut1, ind.getGene(cut2));
        ind.setGene(cut2, aux);
    }

    @Override
    public String toString() {
        return "Swap";
    }

}