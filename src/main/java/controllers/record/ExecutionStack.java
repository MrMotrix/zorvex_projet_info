package controllers.record;

import java.util.Stack;

import graphics.GraphicalRepresentation;

public class ExecutionStack {

    private Stack<ExecutionRecord> history = new Stack<>();
    protected int currentLine = 0;
    public void push(ExecutionRecord record) {history.push(record);}

    public void undo(GraphicalRepresentation rep) {
        if (!history.isEmpty()) {
            ExecutionRecord record = history.pop();
            record.undo(rep);
            currentLine = record.nbLine();
        }
    }
    public int currentLine(){return currentLine;}
    public Stack<ExecutionRecord> getHistory() {return history;}
    public void clear(){history.clear();}
}
