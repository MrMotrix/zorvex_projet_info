package interpreter;

public record BinaryOperation(Expression left, Expression right, BinaryOperator op) implements Expression {
    public ZorvexValue value(Context context) {
        return op.apply(left.value(context), right.value(context));
    }
}
