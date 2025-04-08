package interpreter.exceptions;

import interpreter.ZorvexType;
import interpreter.ZorvexValue;

public class UnexpectedTypeException extends RuntimeError {
    private final ZorvexValue value;
    private final ZorvexType expected;

    public UnexpectedTypeException(ZorvexValue value, ZorvexType expected) {
        super("Type inattendu : attendait " + expected + " obtenu " + value);
        this.value = value;
        this.expected = expected;
    }
}
