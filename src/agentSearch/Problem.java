package agentSearch;

import java.util.List;

public abstract class Problem<S extends State> {

    protected S initialState;
    protected Heuristic heuristic;
    protected List<Action> actions;

    public void setInitialState(S initialState) {
        this.initialState = initialState;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Problem(S initialState) {
        this.initialState = initialState;
    }

    public abstract List<S> executeActions(S state) throws CloneNotSupportedException;

    public abstract boolean isGoal(S state);

    public S getInitialState() {
        return initialState;
    }

    public double computePathCost(List<Action> path) {
        double cost = 0;
        for (Action operator : path) {
            cost += operator.getCost();
        }
        return cost;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
