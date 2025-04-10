package controllers.record;

import graphics.GraphicalRepresentation;
import graphics.ModificationType;

public class IterableModifyRecord
 extends ExecutionRecord
{
    
    private ModificationType type;
private int position;
    private String oldValue;

    public IterableModifyRecord(int id, ModificationType type, int position, String oldValue, int nbline) {
        super(id, nbline);
        this.type = type;
        this.position = position;
        this.oldValue = oldValue;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.updateElement(id, type, oldValue, position);
    }
}
