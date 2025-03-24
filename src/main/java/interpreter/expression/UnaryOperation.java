package interpreter.expression;

import java.util.function.Function;

import interpreter.Context;
import interpreter.ZorvexValue;

public record UnaryOperation(Expression expr, UnaryOperator op) implements Expression {
    public ZorvexValue value(Context context) {
        return op.apply(expr.value(context));
    }

    public String printValue() {
        return "(" + op.toString() + expr.printValue() + ")";
    }
}
