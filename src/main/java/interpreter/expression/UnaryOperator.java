package interpreter.expression;

import interpreter.ZorvexValue;

public interface UnaryOperator {
    ZorvexValue apply(ZorvexValue obj);
}