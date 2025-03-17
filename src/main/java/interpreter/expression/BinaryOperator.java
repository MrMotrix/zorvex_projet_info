package interpreter.expression;

import interpreter.ZorvexValue;

public interface BinaryOperator {
    ZorvexValue apply(ZorvexValue obj1, ZorvexValue obj2);
    String toString();
}