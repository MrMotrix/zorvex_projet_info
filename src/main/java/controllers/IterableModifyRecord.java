package controllers;

import graphics.GraphicalRepresentation;
import graphics.IterableGraphicalObject;
import graphics.ModificationType;

public class IterableModifyRecord
 extends ExecutionRecord
{
    
    private ModificationType type;
private int position;
    private String oldValue;

    public IterableModifyRecord(int id, ModificationType type, int position, String oldValue) {
        super(id);
        this.type = type;
        this.position = position;
        this.oldValue = oldValue;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.updateElement(id, type, oldValue, position);
    }
}
