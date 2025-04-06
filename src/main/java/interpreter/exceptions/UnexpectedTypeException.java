package interpreter.exceptions;

import interpreter.ZorvexType;
import interpreter.ZorvexValue;

public class UnexpectedTypeException extends RuntimeError {
    private final ZorvexValue value;
    private final ZorvexType expected;

    public UnexpectedTypeException(int lineNumber, ZorvexValue value, ZorvexType expected) {
        super(lineNumber);
        this.value = value;
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "Unexpected type on line " + lineNumber + " expected " + expected + " got " + value;
    }
    
}
