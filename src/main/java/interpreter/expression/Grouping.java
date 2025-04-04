package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;

public non-sealed class Grouping implements Expression {
    private Expression expr;

    public Grouping(Expression expr) {
        this.expr = expr;
    }

    public Expression expr() {
        return expr;
    }
    public ZorvexValue value(Context context) {
        return expr.value(context);
    }

    public String printValue() {
        return expr.printValue();
    }

    public void setExpression(ZorvexValue value) {
        expr = new Literal(value);
    }

}
