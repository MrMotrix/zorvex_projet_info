package interpreter.expression;

import java.util.function.Function;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public record BinaryOperation(Expression left, Expression right, BinaryOperator op) implements Expression {
    public ZorvexValue value(Context context) throws RuntimeError {
        return op.apply(left.value(context), right.value(context));
    }

    public String printValue() {
        return "(" + left.printValue() + " " + op.toString() + " " + right.printValue() + ")";
    }

    public Expression copy() {
        return new BinaryOperation(left.copy(), right.copy(), op);
    }
}
