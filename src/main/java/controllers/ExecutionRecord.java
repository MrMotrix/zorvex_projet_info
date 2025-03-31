package controllers;

import graphics.GraphicalRepresentation;

public abstract class ExecutionRecord {
    protected String variableName;

    public ExecutionRecord(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }

    public abstract void undo(GraphicalRepresentation rep);
}
