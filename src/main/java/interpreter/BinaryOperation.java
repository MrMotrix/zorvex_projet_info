package interpreter;

import java.util.function.Function;

public record BinaryOperation(Expression left, Expression right, BinaryOperator op) implements Expression {
    public ZorvexValue value(Context context) {
        return op.apply(left.value(context), right.value(context));
    }

    public String printValue() {
        Function<Expression, String> addParenthesis = (Expression x) -> x instanceof Literal ? x.printValue() : "(" + x.printValue() + ")";
        return addParenthesis.apply(left) + " " + op.toString() + " " + addParenthesis.apply(right);
    }
}
