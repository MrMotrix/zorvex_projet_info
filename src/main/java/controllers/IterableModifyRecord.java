package controllers;

import graphics.GraphicalRepresentation;
import graphics.IterableGraphicalObject;
import graphics.ModificationType;

public class IterableModifyRecord extends ExecutionRecord{
    
    private ModificationType type;
    private int position;
    private String oldValue;

    public IterableModifyRecord(String variableName, ModificationType type, int position, String oldValue) {
        super(variableName);
        this.type = type;
        this.position = position;
        this.oldValue = oldValue;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.updateElement(variableName, type, oldValue, position);
    }
}
