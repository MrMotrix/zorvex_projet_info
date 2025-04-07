package controllers;

import graphics.GraphicalRepresentation;

public abstract class ExecutionRecord {
    protected int id;

    public ExecutionRecord(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void undo(GraphicalRepresentation rep);
}
