package warehouse;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {

    private int[][] matrix;
    private int lineAgent, columnAgent;
    private int lineExit;
    private int columnExit;
    private int steps;

    public WarehouseState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if(matrix[i][j] == Properties.AGENT){
                    lineExit = lineAgent = i;
                    columnExit = columnAgent = j;
                }
            }
        }
    }

    public WarehouseState(int[][] matrix, int lineAgent, int columnAgent, int lineExit, int columnExit) {
        this.matrix = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
        this.lineAgent = lineAgent;
        this.columnAgent = columnAgent;
        this.lineExit = lineExit;
        this.columnExit = columnExit;
    }

    public int getLineExit() {
        return lineExit;
    }

    public int getColumnExit() {
        return columnExit;
    }

    public double compute (int lineGoal, int columnGoal){
        //heuristica
        if(lineGoal == lineExit && columnGoal == columnExit){
            return Math.abs(lineAgent-lineGoal) + Math.abs(columnAgent-(columnGoal));
        }
        return Math.abs(lineAgent-lineGoal) + Math.abs(columnAgent-(columnGoal+1));
    }


    public void executeAction(Action action) {
        action.execute(this);
        this.steps++;
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        this.steps++;
        fireUpdatedEnvironment();
    }


    public boolean canMoveUp() {
        return (this.lineAgent != 0 && (this.matrix[this.lineAgent - 1][this.columnAgent] == Properties.EMPTY));
    }

    public boolean canMoveRight() {
        return ((this.columnAgent != this.matrix.length - 1) && (this.matrix[lineAgent][this.columnAgent + 1] == Properties.EMPTY));
    }

    public boolean canMoveDown() {
        return ((this.lineAgent != this.matrix.length - 1) && (this.matrix[lineAgent + 1][this.columnAgent] == Properties.EMPTY));
    }

    public boolean canMoveLeft() {
        return (this.columnAgent != 0 && (this.matrix[this.lineAgent][this.columnAgent - 1] == Properties.EMPTY));
    }

    public void moveUp() {
        this.matrix[this.lineAgent - 1][this.columnAgent] = Properties.AGENT;
        this.matrix[this.lineAgent][this.columnAgent] = Properties.EMPTY;
        this.lineAgent--;
    }

    public void moveRight() {
        this.matrix[this.lineAgent][this.columnAgent + 1] = Properties.AGENT;
        this.matrix[this.lineAgent][this.columnAgent] = Properties.EMPTY;
        this.columnAgent++;
    }

    public void moveDown() {
        this.matrix[this.lineAgent + 1][this.columnAgent] = Properties.AGENT;
        this.matrix[this.lineAgent][this.columnAgent] = Properties.EMPTY;
        this.lineAgent++;
    }

    public void moveLeft() {
        this.matrix[this.lineAgent][this.columnAgent - 1] = Properties.AGENT;
        this.matrix[this.lineAgent][this.columnAgent] = Properties.EMPTY;
        this.columnAgent--;
    }

    public void setCellAgent(int line, int column) {
        this.matrix[line][column] = Properties.AGENT;
        this.matrix[this.lineAgent][this.columnAgent] = Properties.EMPTY;
        this.lineAgent = line;
        this.columnAgent = column;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return matrix.length;
    }

    public Color getCellColor(int line, int column) {
        if (line == lineExit && column == columnExit && (line != lineAgent || column != columnAgent))
            return Properties.COLOREXIT;

        switch (matrix[line][column]) {
            case Properties.AGENT:
                return Properties.COLORAGENT;
            case Properties.SHELF:
                return Properties.COLORSHELF;
            default:
                return Properties.COLOREMPTY;
        }
    }

    public int getLineAgent() {
        return lineAgent;
    }

    public int getColumnAgent() {
        return columnAgent;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WarehouseState)) {
            return false;
        }

        WarehouseState o = (WarehouseState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public WarehouseState clone() {
        return new WarehouseState(this.matrix, this.lineAgent, this.columnAgent, this.lineExit, this.columnExit);
    }

    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

}
