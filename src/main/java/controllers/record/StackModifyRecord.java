package controllers.record;

import graphics.GraphicalRepresentation;
import graphics.ModificationType;

public class StackModifyRecord extends ExecutionRecord {
    private String oldValue;
    private ModificationType type;

    public StackModifyRecord(int id, String oldValue, ModificationType type, int nbline) {
        super(id, nbline);
        this.oldValue = oldValue;
        this.type = type;
    }


    @Override
    public void undo(GraphicalRepresentation rep) {
        if (type == ModificationType.POP) rep.updateElement(id, ModificationType.PUSH, oldValue, 0);
        else rep.updateElement(id, ModificationType.POP, oldValue, 0);
    }

}
