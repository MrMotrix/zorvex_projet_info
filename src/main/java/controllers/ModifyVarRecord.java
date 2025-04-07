package controllers;

import graphics.GraphicalRepresentation;
import graphics.ModificationType;

public class ModifyVarRecord extends ExecutionRecord {
    private String oldValue;

    public ModifyVarRecord(int id, String oldValue, int nbline) {
        super(id, nbline);
        this.oldValue = oldValue;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.updateElement(id, ModificationType.UPDATE, oldValue, 0);
    }
}