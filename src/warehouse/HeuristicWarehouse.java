package warehouse;

import agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    @Override
    public double compute(WarehouseState state){
        return state.calculate_distance(problem.getGoalPosition());
    }

    @Override
    public String toString(){
        return "Heuristic: Distance to goal";
    }
}