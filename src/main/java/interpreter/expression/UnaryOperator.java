package interpreter.expression;

import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public interface UnaryOperator {
    ZorvexValue apply(ZorvexValue obj) throws RuntimeError;
}