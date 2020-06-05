package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

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

        fitness = 0;
        for (Request r : problem.getRequests()) {

            Cell cell1 = problem.getCellAgent(); //celula do agente
            Cell cell2 = problem.getShelves().get(getShelfPos(genome,r.getRequest()[0]));
            /**
             //distância do Agente à primeira prateleira do pedido
             for (Pair p : problemblem.get.getPairs()) {
             if ((p.getCell1().equals(cell1) && p.getCell2().equals(cell2)) || (p.getCell1().equals(cell2) && p.getCell2().equals(cell1))) {
             fitness += p.getValue();
             break;
             }
             }*/
            Pair pAux = new Pair(cell1,cell2);
            Pair pAux2 = new Pair(cell2,cell1);
            if(problem.getPairsHash().containsKey(pAux.hashCode())){
                fitness+=problem.getPairsHash().get(pAux.hashCode());
            }else{
                fitness+=problem.getPairsHash().get(pAux2.hashCode());
            }

            for (int i = 0; i < r.getRequest().length-1; i++) {
                cell1 = problem.getShelves().get(getShelfPos(genome,r.getRequest()[i]));
                cell2= problem.getShelves().get(getShelfPos(genome,r.getRequest()[i+1]));

                //distância do entre prateleiras do pedido
                /*
                for (Pair p : problem.getPairs()) {
                    if ((p.getCell1().equals(cell1) && p.getCell2().equals(cell2)) || (p.getCell1().equals(cell2) && p.getCell2().equals(cell1))) {
                        fitness += p.getValue();
                        break;
                    }
                }
                */

                pAux = new Pair(cell1,cell2);
                pAux2 = new Pair(cell2,cell1);
                if(problem.getPairsHash().containsKey(pAux.hashCode())){
                    fitness+=problem.getPairsHash().get(pAux.hashCode());
                }else{
                    fitness+=problem.getPairsHash().get(pAux2.hashCode());
                }

            }



            cell1 = problem.getShelves().get(getShelfPos(genome,r.getRequest()[r.getRequest().length-1]));
            cell2 = problem.getExit();

            //distância da última prateleira à porta
            /*
            for (Pair p : problem.getPairs()) {
                if ((p.getCell1().equals(cell1) && p.getCell2().equals(cell2)) || (p.getCell1().equals(cell2) && p.getCell2().equals(cell1))) {
                    fitness += p.getValue();
                    break;
                }
            }
            */

            pAux = new Pair(cell1,cell2);
            pAux2 = new Pair(cell2,cell1);
            if(problem.getPairsHash().containsKey(pAux.hashCode())){
                fitness+=problem.getPairsHash().get(pAux.hashCode());
            }else{
                fitness+=problem.getPairsHash().get(pAux2.hashCode());
            }

        }
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
