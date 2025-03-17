package interpreter.expression;

import interpreter.Token;
import interpreter.ZorvexValue;

public class UBinaryOperator implements BinaryOperator {
    private Token operation;

    public UBinaryOperator(Token token) {
        operation = token;
    }

    @Override
    public ZorvexValue apply(ZorvexValue obj1, ZorvexValue obj2) {
        return switch (operation.type()) {
            case PLUS -> new ZorvexValue(obj1.asInteger() + obj2.asInteger());
            case MOINS -> new ZorvexValue(obj1.asInteger() - obj2.asInteger());
            case FOIS -> new ZorvexValue(obj1.asInteger() * obj2.asInteger());
            case DIVISE -> new ZorvexValue(obj1.asInteger() / obj2.asInteger());
            default -> new ZorvexValue(0);
        };
    }

    @Override
    public String toString() {
        return operation.type().characters();
    }
    
}
