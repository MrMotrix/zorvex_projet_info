package interpreter.expression;

import interpreter.Token;
import interpreter.ZorvexValue;

public class UUnaryOperator implements UnaryOperator {
    private Token operation;

    public UUnaryOperator(Token token) {
        operation = token;
    }

    @Override
    public ZorvexValue apply(ZorvexValue obj) {
        return switch (operation.type()) {
            case MOINS -> new ZorvexValue(-(obj.asInteger()));
            default -> new ZorvexValue(0);
        };
    }

    @Override
    public String toString() {
        return operation.type().characters();
    }
    
}
