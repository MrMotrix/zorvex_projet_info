package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public final class Assigner implements Instruction {
    private final String variableName;
    private ZorvexValue result;
    private Expression expression;
    
    public Assigner(String variableName, Expression expression) {
        this.expression = expression;
        this.variableName = variableName;
    }

    public Expression expression() {
        return expression;
    }

    public Optional<ZorvexValue> interpret(Context context) {
        context.assignVariable(variableName, result);
        return Optional.empty();
    } 

    public String variableName() {
        return variableName;
    }

    public void serveResult(ZorvexValue result) {
        this.result = result;
    }
}
