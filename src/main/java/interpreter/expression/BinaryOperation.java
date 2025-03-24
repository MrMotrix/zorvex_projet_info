package interpreter.expression;

import java.util.function.Function;

import interpreter.Context;
import interpreter.ZorvexValue;

public record BinaryOperation(Expression left, Expression right, BinaryOperator op) implements Expression {
    public ZorvexValue value(Context context) {
        return op.apply(left.value(context), right.value(context));
    }

    public String printValue() {
        return "(" + left.printValue() + " " + op.toString() + " " + right.printValue() + ")";
    }
}
