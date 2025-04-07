package controllers;

import graphics.AbstractGraphicalObject;
import graphics.GraphicalObject;
import graphics.GraphicalRepresentation;

public class DeleteRecord extends ExecutionRecord{
private AbstractGraphicalObject obj;

    public DeleteRecord(int id, AbstractGraphicalObject obj) {
        super(id);
        this.obj = obj;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.addElement(id, obj);
    }
}
