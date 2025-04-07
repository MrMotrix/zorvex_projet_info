package controllers;

import graphics.GraphicalRepresentation;

public class AfficherRecord extends ExecutionRecord {

    private String content;

    public AfficherRecord(int id, String content, int nbline) {
        super(id, nbline);
        this.content = content;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        //nothing to undo
        return;
    }

}
