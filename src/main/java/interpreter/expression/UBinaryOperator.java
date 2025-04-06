package interpreter.expression;

import interpreter.Token;
import interpreter.TokenType;
import interpreter.ZorvexType;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;
import interpreter.exceptions.UnexpectedTypeException;
import interpreter.exceptions.UnsupportedOperationError;

public class UBinaryOperator implements BinaryOperator {
    private Token operation;

    public UBinaryOperator(Token token) {
        operation = token;
    }

    @Override
    public ZorvexValue apply(ZorvexValue obj1, ZorvexValue obj2) throws RuntimeError {
        if (operation.type() == TokenType.EGAL) 
            return new ZorvexValue(obj1.equals(obj2) ? 1 : 0);
        
        else if (operation.type() == TokenType.DIFFERENT) 
            return new ZorvexValue(obj1.equals(obj2) ? 0 : 1);
        

        if (obj1.isString() && !obj2.isString() || obj2.isString() && !obj1.isString()) 
            return apply(new ZorvexValue(obj1.asString()), new ZorvexValue(obj2.asString()));
        
        if (obj1.isString()) {
            if (operation.type() != TokenType.PLUS)
                throw new UnsupportedOperationError(-1, obj1, obj2, operation.type());
            return new ZorvexValue(obj1.asString() + obj2.asString());
        } 
        
        if (!obj1.isInteger()) 
            throw new UnexpectedTypeException(-1, obj1, ZorvexType.INTEGER);
        
        if (!obj2.isInteger())
            throw new UnexpectedTypeException(-1, obj1, ZorvexType.INTEGER);


        return switch (operation.type()) {
            case PLUS -> new ZorvexValue(obj1.asInteger() + obj2.asInteger());
            case MOINS -> new ZorvexValue(obj1.asInteger() - obj2.asInteger());
            case FOIS -> new ZorvexValue(obj1.asInteger() * obj2.asInteger());
            case DIVISE -> new ZorvexValue(obj1.asInteger() / obj2.asInteger());
            case PLUS_PETIT -> new ZorvexValue(obj1.asInteger() < obj2.asInteger() ? 1 : 0);
            case PLUS_GRAND -> new ZorvexValue(obj1.asInteger() > obj2.asInteger() ? 1 : 0);
            case PETIT_EGAL -> new ZorvexValue(obj1.asInteger() <= obj2.asInteger() ? 1 : 0);
            case GRAND_EGAL -> new ZorvexValue(obj1.asInteger() >= obj2.asInteger() ? 1 : 0);
            case OU -> new ZorvexValue(obj1.asInteger() + obj2.asInteger() >= 0 ? 1 : 0);
            case ET -> new ZorvexValue(obj2.asInteger() * obj1.asInteger() == 0 ? 0 : 1);
            default -> new ZorvexValue(0);
        };
    }

    @Override
    public String toString() {
        return operation.type().characters();
    }
    
}
