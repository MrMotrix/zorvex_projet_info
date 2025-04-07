package controllers;

import graphics.GraphicalRepresentation;

public abstract class ExecutionRecord {
    protected int id;
    protected int nbline;

    public ExecutionRecord(int id, int nbline) {
        this.id = id;
        this.nbline = nbline;
    }

    public int getId() {return id;}
    public int nbLine(){return nbline;}

    public abstract void undo(GraphicalRepresentation rep);
}
