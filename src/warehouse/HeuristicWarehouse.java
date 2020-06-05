package warehouse;

import agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
//    TODO : Verify compute function is this enough??
    @Override
    public double compute(WarehouseState state){
        return state.calculate_distance(problem.getGoalPosition());
    }

    @Override
    public String toString(){
        return "Distance to goal";
    }
}