package controllers;

import graphics.GraphicalRepresentation;
import graphics.ModificationType;

public class ModifyVarRecord extends ExecutionRecord {
    private String oldValue;

    public ModifyVarRecord(String variableName, String oldValue) {
        super(variableName);
        this.oldValue = oldValue;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.updateElement(variableName, ModificationType.UPDATE, oldValue, 0);
    }
}