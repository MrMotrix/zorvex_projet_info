package interpreter.expression;

import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public interface BinaryOperator {
    ZorvexValue apply(ZorvexValue obj1, ZorvexValue obj2) throws RuntimeError ;
    String toString();
}