package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public final class Afficher implements Instruction {
    private ZorvexValue printedResult;
    private Expression expression;

    public Afficher(Expression expr) {
        this.expression = expr;
    }

    public Expression expression() {
        return expression;
    }

    public Optional<ZorvexValue> interpret(Context context) {
        System.out.println(printedResult);
        return Optional.empty();
    } 

    public void setResult(ZorvexValue val) {
        this.printedResult = val;
    }
    
    public ZorvexValue result() {
        return printedResult;
    }
}
