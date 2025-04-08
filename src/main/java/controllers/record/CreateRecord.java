package controllers.record;

import graphics.AbstractGraphicalObject;
import graphics.GraphicalObject;
import graphics.GraphicalRepresentation;

public class CreateRecord extends ExecutionRecord {
    private AbstractGraphicalObject graphic;

    public CreateRecord(int id, AbstractGraphicalObject graphic, int nbline) {
        super(id, nbline);
        this.graphic = graphic;
    }

    @Override
    public void undo(GraphicalRepresentation rep) {
        rep.deleteElement(graphic.getID());
    }
}
