package warehouse;

import agentSearch.Action;
import agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {
    private Cell goalPosition;
    private LinkedList<Action> listOfActions;

    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);
        this.goalPosition = goalPosition;
        this.listOfActions = new LinkedList<Action>() {
            {
                add(new ActionUp());
                add(new ActionRight());
                add(new ActionDown());
                add(new ActionLeft());
            }
        };
    }

    public Cell getGoalPosition() {
        return goalPosition;
    }

    @Override
    public LinkedList<S> executeActions(S state) {
        LinkedList<S> listOfSuccessors = new LinkedList<>();
        for (Action action: listOfActions) {
            if (action.isValid(state)) {
                S successor = (S)state.clone();
                action.execute(successor);
                listOfSuccessors.add(successor);
            }
        }
        return listOfSuccessors;
    }

    public boolean isGoal(S state) {
        /**
         *
         * Checks if the goal is to go to the exit
         *
         * if it is then returns the operator to validate if he is in the exit
         *
         * if the goal is not the exit then the goal is to be to the right of the shelf
         *
         * return the boolean to verify that too
         *
         * */
        return state.isGoalExit(getGoalPosition().getLine(), getGoalPosition().getColumn())
                ? state.getLineAgent()==getGoalPosition().getLine() && state.getColumnAgent()==getGoalPosition().getColumn()
                : state.getLineAgent()==getGoalPosition().getLine() && state.getColumnAgent()==getGoalPosition().getColumn()+1;
    }
}
