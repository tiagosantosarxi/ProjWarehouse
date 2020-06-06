package warehouse;

import ga.Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {

    private LinkedList<Cell> shelves;
    private Cell cellAgent;
    private Cell exit;
    private ArrayList<Request> requests;
    private int numProducts;
    private HashMap<String, Pair> pairs;

    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.shelves = agentSearch.getShelves();
        this.cellAgent = agentSearch.getCellAgent();
        this.exit = agentSearch.getExit();
        this.requests=agentSearch.getRequests();
        this.numProducts = agentSearch.getNumProducts();
        this.pairs = new HashMap<String, Pair>();
        for (int i = 0; i < agentSearch.getPairs().size(); i++) {
            Pair pair = (Pair) agentSearch.getPairs().get(i);
            pairs.put(pair.toString().split(":")[0], pair);
        }
    }

    public Pair getPair(Cell first, Cell second) {
        Pair pair = null;
        for (int i = 0; i < this.pairs.size(); i++) {
            String combination = first.toString() + " / " + second.toString();
            String reverse = second.toString() + " / " + first.toString();
            pair = this.pairs.get(combination) != null ? this.pairs.get(combination) : this.pairs.get(reverse);
            if (pair != null) {
                break;
            }
        }
        return pair;
    }

    public LinkedList<Cell> getShelves() {
        return shelves;
    }

    public Cell getCellAgent() {
        return cellAgent;
    }

    public Cell getExit() {
        return exit;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public int getNumProducts() {
        return numProducts;
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this,shelves.size());
    }
}
