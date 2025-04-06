package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public sealed interface Expression permits Literal, Variable, BinaryOperation, UnaryOperation, Grouping, FunctionCall {
    ZorvexValue value(Context context) throws RuntimeError;
    String printValue();
    Expression copy();
}

