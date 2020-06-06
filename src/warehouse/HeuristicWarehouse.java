package warehouse;

import agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    @Override
    public double compute(WarehouseState state){
        return state.compute(problem.getGoalPosition());
    }

    @Override
    public String toString(){
        return "Heuristica: Distância Linear";
    }
}