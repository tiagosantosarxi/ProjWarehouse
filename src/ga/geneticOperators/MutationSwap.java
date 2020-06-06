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
        /**
         * Perform a swap mutation
         * Simply swap gene from position 1 to position 2
         * */
        int pos1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int pos2;
        do {
            pos2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        } while (pos1 == pos2);
        int aux = ind.getGene(pos1);
        ind.setGene(pos1, ind.getGene(pos2));
        ind.setGene(pos2, aux);
    }

    @Override
    public String toString() {
        return "Swap";
    }

}