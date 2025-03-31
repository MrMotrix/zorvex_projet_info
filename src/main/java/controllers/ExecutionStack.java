package controllers;

import java.util.Stack;

import graphics.GraphicalRepresentation;

public class ExecutionStack {

    private Stack<ExecutionRecord> history = new Stack<>();

    public void push(ExecutionRecord record) {
        history.push(record);
    }

    public void undo(GraphicalRepresentation rep) {
        if (!history.isEmpty()) {
            ExecutionRecord record = history.pop();
            record.undo(rep);
        }
    }
}
