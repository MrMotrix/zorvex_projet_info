package interpreter.expression;

import interpreter.Token;
import interpreter.TokenType;
import interpreter.ZorvexType;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;
import interpreter.exceptions.UnexpectedTypeException;
import interpreter.exceptions.UnsupportedOperationError;

public class UUnaryOperator implements UnaryOperator {
    private Token operation;

    public UUnaryOperator(Token token) {
        operation = token;
    }

    @Override
    public ZorvexValue apply(ZorvexValue obj) throws RuntimeError {
        if (!obj.isInteger())
            throw new UnsupportedOperationError(-1, obj, null, operation.type());
        if (operation.type() != TokenType.MOINS)
            throw new UnsupportedOperationError(-1, obj, null, operation.type());
        return new ZorvexValue(-(obj.asInteger()));
    }

    @Override
    public String toString() {
        return operation.type().characters();
    }
    
}
