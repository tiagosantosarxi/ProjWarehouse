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
        for (int line = 0; line < matrix.length; line++) {
            for (int column = 0; column < matrix.length; column++) {
                this.matrix[line][column] = matrix[line][column];
                if(matrix[line][column] == Properties.AGENT){
                    lineExit = lineAgent = line;
                    columnExit = columnAgent = column;
                }
            }
        }
    }

    public void executeAction(Action action) {
        action.execute(this);
        this.steps++;
        fireUpdatedEnvironment();
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        this.steps++;
        fireUpdatedEnvironment();
    }


    public boolean canMoveUp() { return lineAgent != 0 && matrix[lineAgent-1][columnAgent]!=Properties.SHELF; }

    public boolean canMoveRight() { return columnAgent != matrix.length-1 && matrix[lineAgent][columnAgent+1]!=Properties.SHELF; }

    public boolean canMoveDown() { return lineAgent != matrix.length-1 && matrix[lineAgent+1][columnAgent]!=Properties.SHELF; }

    public boolean canMoveLeft() { return columnAgent != 0 && matrix[lineAgent][columnAgent-1]!=Properties.SHELF; }

    public void moveUp() {
        matrix[--lineAgent][columnAgent] = Properties.AGENT;
        matrix[lineAgent][columnAgent] = Properties.EMPTY;
    }

    public void moveRight() {
        matrix[lineAgent][++columnAgent] = Properties.AGENT;
        matrix[lineAgent][columnAgent] = Properties.EMPTY;
    }

    public void moveDown() {
        matrix[++lineAgent][columnAgent] = Properties.AGENT;
        matrix[lineAgent][columnAgent] = Properties.EMPTY;
    }

    public void moveLeft() {
        matrix[lineAgent][--columnAgent] = Properties.AGENT;
        matrix[lineAgent][columnAgent] = Properties.EMPTY;
    }

    public void setCellAgent(int line, int column) {
        matrix[lineAgent][columnAgent] = Properties.EMPTY;
        lineAgent = line;
        columnAgent = column;
        matrix[lineAgent][lineAgent] = Properties.AGENT;
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
        // Use enhanced for Loop
        for (int[] ints : matrix) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(ints[j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public WarehouseState clone() throws CloneNotSupportedException {
        return (WarehouseState) super.clone();
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

    public int getLineExit() {
        return lineExit;
    }

    public void setLineExit(int lineExit) {
        this.lineExit = lineExit;
    }

    public int getColumnExit() {
        return columnExit;
    }

    public void setColumnExit(int columnExit) {
        this.columnExit = columnExit;
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

}
