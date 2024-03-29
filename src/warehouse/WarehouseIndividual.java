package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        /**
         * Generate new individual
         *
         * if any random value generated is present in the genome then generate a new random value
         * if the number is not present in the genome then fill the value of genome[i] with the randomly generated value
         *
         * to increase performance we don't loop the whole size in inner loop, we only loop the size we have filled so far (i)
         * */
        super(problem, size);
        int randInt;
        boolean exitCondition;
        for (int i = 0; i < size; i++) {
            do {
                randInt = GeneticAlgorithm.random.nextInt(size) + 1;
                exitCondition = false;
                for (int j = 0; j < i; j++) {
                    if (genome[j] == randInt) {
                        exitCondition = true;
                        break;
                    }
                }
            } while (exitCondition);
            genome[i] = randInt;
        }
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        /**
         * Compute fitness of individual
         *
         * Sum of:
         *      distance between the agent and the first request
         *      distance between requests
         *      distance between last request and exit
         *
         *  */
        int distanceToTargets = 0;
        for (Request request : problem.getRequests()) {
            Cell agent = problem.getCellAgent();
            Cell firstRequest = problem.getShelves().get(getShelfPos(genome,request.getRequest()[0]));
            distanceToTargets += problem.getPair(agent, firstRequest).getValue();
            Cell thisRequest = null;
            Cell nextRequest = null;
            for (int i = 0; i < request.getRequest().length - 1; i++) {
                thisRequest = problem.getShelves().get(getShelfPos(genome,request.getRequest()[i]));
                nextRequest = problem.getShelves().get(getShelfPos(genome,request.getRequest()[i+1]));
                distanceToTargets += problem.getPair(thisRequest, nextRequest).getValue();
            }
            distanceToTargets += problem.getPair(problem.getExit(), problem.getShelves().get(getShelfPos(genome, request.getRequest()[request.getRequest().length - 1]))).getValue();
        }
        fitness = distanceToTargets;
        return fitness;
    }

    public static int getShelfPos(int[] genome, int value) {
        int i = 0;
        for (i = 0; i < genome.length; i++) {
            if (genome[i] == value) {
                break;
            }
        }
        return i;
    }

    public int getProductInShelf(int line, int column) {
        /**
         *
         * Finds the shelf with the line and column
         * makes sure the shelf has the product
         * returns the genome for it
         *
         * */
        int i = 0;
        LinkedList<Cell> shelves = problem.getShelves();
        for (Cell shelf : shelves) {
            if (column == shelf.getColumn() && line == shelf.getLine() && genome[i] <= problem.getNumProducts()){
                break;
            }
            i++;
        }
        return i < genome.length
                ? genome[i]
                : 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i < genome.length; i++) {
            sb.append(genome[i]).append(" ");
        }
        sb.append("\nShelfs: \n");
        for (int i = 0; i < genome.length; i++) {
            String prod = genome[i] < problem.getNumProducts() ? genome[i] + "" : "Empty shelf";
            sb.append(problem.getShelves().get(i) + " - " + prod + "\n");
        }
        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(WarehouseIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public WarehouseIndividual clone() {
        return new WarehouseIndividual(this);

    }
}
