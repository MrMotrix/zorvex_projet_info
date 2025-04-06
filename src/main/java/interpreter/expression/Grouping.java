package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public non-sealed class Grouping implements Expression {
    private Expression expr;

    public Grouping(Expression expr) {
        this.expr = expr;
    }

    public Expression expr() {
        return expr;
    }
    public ZorvexValue value(Context context) throws RuntimeError {
        return expr.value(context);
    }

    public String printValue() {
        return expr.printValue();
    }

    public void setExpression(ZorvexValue value) {
        expr = new Literal(value);
    }

    public Expression copy() {
        return new Grouping(expr.copy());
    }
}
