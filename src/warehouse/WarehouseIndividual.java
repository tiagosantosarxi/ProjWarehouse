package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {
    private int pathCost;

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

        for (Request request : problem.getRequests()) {
            int[] requestProducts = request.getRequest();

            for (int i = 0; i < requestProducts.length - 1; i++) {
                for (int j = i + 1; j < requestProducts.length; j++) {
                    int first = getShelfPos(genome, requestProducts[i]);
                    int second = getShelfPos(genome, requestProducts[j]);
                    Cell firstCell = problem.getShelves().get(first);
                    Cell secondCell = problem.getShelves().get(second);
                    if (firstCell != secondCell) {
                        pathCost += problem.getPair(firstCell, secondCell).getValue();
                    }
                }
            }
            pathCost += problem.getPair(problem.getShelves().get(getShelfPos(genome, requestProducts[0])), problem.getExit()).getValue();
            pathCost += problem.getPair(problem.getExit(), problem.getShelves().get(getShelfPos(genome, requestProducts[requestProducts.length - 1]))).getValue();
        }
        fitness = pathCost;
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
            //this method might require changes
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
