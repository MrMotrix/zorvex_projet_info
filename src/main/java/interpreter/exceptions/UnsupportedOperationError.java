package interpreter.exceptions;

import interpreter.TokenType;
import interpreter.ZorvexType;
import interpreter.ZorvexValue;

public class UnsupportedOperationError extends RuntimeError {
    private final ZorvexValue value1;
    private final ZorvexValue value2;
    private final TokenType operator;

    public UnsupportedOperationError(ZorvexValue value1, ZorvexValue value2, TokenType operator) {
        super("Operation non supportée " + operator + " sur les opérandes " + value1 + " et " + value2);
        this.value1 = value1;
        this.value2 = value2;
        this.operator = operator;
    }
}
