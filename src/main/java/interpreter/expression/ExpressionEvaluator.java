package interpreter.expression;

import interpreter.Context;
import interpreter.Parser;
import interpreter.Scanner;
import interpreter.ZorvexValue;
import interpreter.exceptions.SyntaxErrorException;

public class ExpressionEvaluator {
    private Expression expression;
    
    public ExpressionEvaluator(Expression expression) {
        this.expression = expression;
    }

    public FunctionCall getNextFunctionCall() {
        Grouping result = getNextExpression(expression);
        if (result == null) {
            // exception
        }
        if (result.expr() instanceof FunctionCall fc)
            return fc;
        return null;
    }

    public Grouping getFirstNonOkArg(FunctionCall fc) {
        for (Expression expr : fc.args())
            if (getNextExpression(expr) != null)
                return getNextExpression(expr);
        return null;
    }
    
    public Grouping getNextExpression(Expression expression) {
        // we basically browse through each nested expression until we reached a function call
        // only catch is that there's a hack : function calls are always inside of a grouping
        // sorry, it's an ugly solution, but I couldn't think of a way to handle 
        // nested compositions such as f(g(x)) in serveNextFunctionCall
        switch (expression) {
            case BinaryOperation op:
                Grouping result = getNextExpression(op.left());
                if (result != null) return result;
                return getNextExpression(op.right());
            case UnaryOperation u:
                return getNextExpression(u.expr());
            case Grouping grouping:
                if (grouping.expr() instanceof FunctionCall fc) {
                    Grouping expr = getFirstNonOkArg(fc);
                    return expr != null ? expr : grouping;
                }
                return getNextExpression(grouping.expr());
            default:
                return null;
        }
    }

    public void serveNextFunctionCall(ZorvexValue returnedValue) {
        Grouping grouping = getNextExpression(expression);

        if (grouping.expr() instanceof FunctionCall fc)
            grouping.setExpression(returnedValue);
        else {
            // exception
        }
    }

    public boolean isEvaluated() {
        return getNextExpression(expression) == null;
    }

    public ZorvexValue result() {
        return expression.value(new Context());
    }
}
