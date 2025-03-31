package controllers;

import graphics.GraphicalObject;
import graphics.GraphicalRepresentation;

public class CreateRecord extends ExecutionRecord {
    private GraphicalObject graphic;

    public CreateRecord(String variableName, GraphicalObject graphic) {
        super(variableName);
        this.graphic = graphic;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.deleteElement(variableName);
    }
}
