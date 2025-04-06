package interpreter.expression;

import java.util.function.Function;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public record UnaryOperation(Expression expr, UnaryOperator op) implements Expression {
    public ZorvexValue value(Context context) throws RuntimeError {
        return op.apply(expr.value(context));
    }

    public String printValue() {
        return "(" + op.toString() + expr.printValue() + ")";
    }

    public Expression copy() {
        return new UnaryOperation(expr.copy(), op);
    }
}
