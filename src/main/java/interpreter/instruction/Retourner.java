package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public final class Retourner implements Instruction {
    private Expression expression;
    private ZorvexValue result;

    public Retourner(Expression expression) {
        this.expression = expression;
    }
    
    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.empty();
    }

    @Override
    public Expression expression() {
        return expression;
    }

    public ZorvexValue result() {
        return result;
    }

    public void setResult(ZorvexValue val) {
        this.result = val;
    }
}
