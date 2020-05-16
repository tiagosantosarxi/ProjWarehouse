package warehouse;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {

    private Cell goalPosition;

    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);
        this.actions = new LinkedList<>(List.of(new ActionUp(),new ActionDown(), new ActionLeft(), new ActionRight()));
        this.goalPosition = goalPosition;
    }

    @Override
    public List<S> executeActions(S state) throws CloneNotSupportedException {
        List<S> successors = new LinkedList<>();
        for (Action action: actions) {
            if (action.isValid(state)){
                S aux_state = (S) state.clone();
                action.execute(aux_state);
                successors.add(aux_state);
            }
        }
        return successors;
    }

    public boolean isGoal(S state) {
        if(isGoalExit(state)) {
            return state.getLineAgent()==goalPosition.getLine() && state.getColumnAgent()==goalPosition.getColumn();
        }
        return state.getLineAgent()==goalPosition.getLine() && state.getColumnAgent()==goalPosition.getColumn()+1;
    }

    public boolean isGoalExit(S state) {
        return goalPosition.getLine() == state.getLineExit() && goalPosition.getColumn() == state.getColumnExit();
    }

    public Cell getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Cell goalPosition) {
        this.goalPosition = goalPosition;
    }
}
