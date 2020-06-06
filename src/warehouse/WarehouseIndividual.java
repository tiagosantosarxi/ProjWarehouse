package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

import java.util.ArrayList;

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
        ArrayList<Request> requests = new ArrayList<>(problem.getRequests());
        for (Request request : requests) {
            //Para cada Request
            int[] requestUnico = request.getRequest();

            //vê as distâncias entre cada um dos produtos a ser recolhidos e adiciona ao pathCost
            for (int i = 0; i < requestUnico.length - 1; i++) {
                for (int j = i + 1; j < requestUnico.length; j++) {
                    int first = getShelfPos(genome, requestUnico[i]);
                    int second = getShelfPos(genome, requestUnico[j]);
                    Cell firstCell = problem.getShelves().get(first);
                    Cell secondCell = problem.getShelves().get(second);
                    if (firstCell != secondCell) {
                        pathCost += problem.getPair(firstCell, secondCell).getValue();
                    }
                }
            }
            //adiciona ainda a distância da porta ao primeiro e do ultimo á porta
            pathCost += problem.getPair(problem.getShelves().get(getShelfPos(genome, requestUnico[0])), problem.getDoor()).getValue();
            pathCost += problem.getPair(problem.getDoor(), problem.getShelves().get(getShelfPos(genome, requestUnico[requestUnico.length - 1]))).getValue();
        }
        fitness = pathCost;
        return fitness;
    }

    public static int getShelfPos(int[] genome, int value) {
        int i = 0;
        /*
        String msg = "";
        for (int j = 0; j < genome.length; j++) {
            msg += genome[j];
        }
        System.out.println("genome: " + msg +" value: "+ value);
        */
        while(value!=genome[i]){
            i++;
        }
        return i;
    }

    //Return the product Id if the shelf in cell [line, column] has some product and 0 otherwise
    public int getProductInShelf(int line, int column) {
        int i=0;
        for (Cell c : problem.getShelves()) {
            if (c.getLine() == line && c.getColumn() == column && genome[i]<= problem.getNumProducts()){
                return genome[i];
            }
            i++;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i < genome.length; i++) {
            if(genome[i]<=problem.getNumProducts()) {
                sb.append(genome[i]).append(" ");
            }else{
                sb.append(0).append(" ");
            }
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
