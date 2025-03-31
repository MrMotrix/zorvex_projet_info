package controllers;

import graphics.GraphicalObject;
import graphics.GraphicalRepresentation;

public class DeleteRecord extends ExecutionRecord{
private GraphicalObject obj;

    public DeleteRecord(String variableName, GraphicalObject obj) {
        super(variableName);
        this.obj = obj;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.addElement(variableName, obj);
    }
}
