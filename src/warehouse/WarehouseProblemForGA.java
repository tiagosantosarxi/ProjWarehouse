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
    private LinkedList<Pair> pairs;
    private HashMap<Integer, Integer> pairsHash;

    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.shelves = agentSearch.getShelves();
        this.cellAgent = agentSearch.getCellAgent();
        this.exit = agentSearch.getExit();
        this.requests=agentSearch.getRequests();
        this.numProducts = agentSearch.getNumProducts();
        this.pairs =  agentSearch.getPairs();
        this.pairsHash = new HashMap<>();
        for (Pair p : pairs) {
            pairsHash.put(p.hashCode(),p.getValue());
        }
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

    public LinkedList<Pair> getPairs() {
        return pairs;
    }

    public HashMap<Integer, Integer> getPairsHash() {
        return pairsHash;
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this,shelves.size());
    }

}
