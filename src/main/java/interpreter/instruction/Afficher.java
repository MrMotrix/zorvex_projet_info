package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public final class Afficher implements Instruction {
    private final Expression expr;
    private ZorvexValue printedResult;

    public Afficher(Expression expr) {
        this.expr = expr;
    }

    public Optional<ZorvexValue> interpret(Context context) {
        printedResult = expr.value(context);
        return Optional.empty();
    } 

    public ZorvexValue result() {
        return printedResult;
    }
}
